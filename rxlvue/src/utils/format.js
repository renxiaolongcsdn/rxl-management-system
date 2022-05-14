// 判断值是否为空
export function isEmpty(obj) {
	if (typeof obj == undefined || obj == "undefined" || obj == null || obj == "") {
		return true;
	} else {
		return false;
	}
}

// 格式化UTC时间
export function formatUTC(value) {
	if (isEmpty(value)) {
		return '-';
	}

	function formatFunc(str) { // 格式化显示
		return str > 9 ? str : '0' + str
	}

	var date = new Date(value);
	var year = date.getFullYear();
	var mon = formatFunc(date.getMonth() + 1);
	var day = formatFunc(date.getDate());
	var hour = date.getHours();
	// var noon = hour >= 12 ? 'PM' : 'AM';
	// hour = hour >= 12 ? hour - 12 : hour;
	hour = formatFunc(hour);
	var min = formatFunc(date.getMinutes());
	return year + '-' + mon + '-' + day + ' ' + hour + ':' + min;
}

// 获取UTC时间戳
export function getUtcTimestamp (value) {
	if (isEmpty(value)) {
		return '-';
	}

	function formatFunc(str) { // 格式化显示
		return str > 9 ? str : '0' + str
	}
	return new Date(value).getTime()
}

// 获取距当前时间差 time：毫秒时间戳
export function getTimeDiff(time) {
    var diff = '';
    var time_diff = new Date().getTime() - time;
    // 计算相差天数  
    var days = Math.floor(time_diff / (24 * 3600 * 1000));
    if (days > 0) {
        diff += days + 'd';
    }
    // 计算相差小时数  
    var leave1 = time_diff % ( 24 * 3600 * 1000); 
    var hours = Math.floor(leave1 / (3600 * 1000));
    if (hours > 0) {
        diff += hours + 'h';
    } else {
        if (diff !== '') {
            diff += hours + 'h';
        }
    }
    // 计算相差分钟数  
    var leave2 =leave1 % (3600 * 1000);
    var minutes = Math.floor(leave2 / (60 * 1000));
    if (minutes > 0) {
        diff += minutes + 'm';
    } else {
        if (diff !== '') {
            diff += minutes + 'm';
        }
    }
    // 计算相差秒数  
    var leave3 = leave2%(60*1000);
    var seconds = Math.round(leave3/1000);
    if (seconds > 0) {
        diff += seconds + 's';
    } else {
        if (diff !== '') {
            diff += seconds + 's';
        }
    }
    return diff;
}

export function arrayUnique(arr) {
    var hash = [];
    for (var i = 0; i < arr.length; i++) {
        for (var j = i + 1; j < arr.length; j++) {
            if (arr[i].id === arr[j].id) {
                ++i;
            }
        }
        hash.push(arr[i]);
    }
    return hash;
}

export function initObj(obj) {
    for (let key in obj) {
        obj[key] = ''
    }
}

/**
 * 将 Date 转化为指定格式
 * 月(M)、日(d)、小时(H)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
 * (new Date()).Format("yyyy-MM-dd HH:mm:ss.S") ==> 2006-07-02 08:09:04.423
 * (new Date()).Format("yyyy-M-d H:m:s.S")      ==> 2006-7-2 8:9:4.18 
 * 
 * @param {*} fmt 
 */
export function dateFormat(fmt, date) {
    let ret;
    const opt = {
        "Y+": date.getFullYear().toString(),        // 年
        "M+": (date.getMonth() + 1).toString(),     // 月
        "D+": date.getDate().toString(),            // 日
        "H+": date.getHours().toString(),           // 时
        "m+": date.getMinutes().toString(),         // 分
        "s+": date.getSeconds().toString()          // 秒
        
        // 有其他格式化字符需求可以继续添加，必须转化成字符串
    };
    for (let k in opt) {
        ret = new RegExp("(" + k + ")").exec(fmt);
        if (ret) {
            fmt = fmt.replace(ret[1], (ret[1].length == 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")))
        };
    };
    return fmt;
}
package com.rxl.dto;

import com.rxl.entity.ArticleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author ren.xiaolong
 * @date 2022/5/1
 * @Description 文章和标签的dto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ArticleLableDto {

    ArticleEntity articleEntity;

    Integer[] lables;

}

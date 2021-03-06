/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.picc.chexian.core.upyun;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.inject.Qualifier;

/**
 * 用upyun来存取文件
 *
 * @author rooseek
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,
         ElementType.FIELD,
         ElementType.TYPE,
         ElementType.PARAMETER})
public @interface UpYunFile {
}

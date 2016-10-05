package com.joergeschmann.tools.loganalyzer.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Is the Annotation to indicate an argument. Classes annotated with this
 * interface are automatically registered in the ArgumentInfoRegistry and are
 * available as arguments.
 * 
 * @author joerg.eschmann@gmail.com
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ArgumentInfo {

    /**
     * The name is used as parameter name.
     * 
     * @return
     */
    String name();

    /**
     * Constructor arguments indicates which constructor of the implementing
     * class should be used to instantiate a new object.
     * 
     * @return
     */
    Class<?>[] constructorArguments();

    /**
     * The description shows an example of how to configure that argument.
     * 
     * @return
     */
    String description();

}

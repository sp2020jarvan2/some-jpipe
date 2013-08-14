package org.swordess.jpipe.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandAnno {

	public String name();
	public String desc() default "";
	public Option[] options() default {};
	
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface Option {
		public String name();
		public String desc();
	}
	
}

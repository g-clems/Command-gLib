package net.feedthemadness.glib.command.executor.listener;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface CmdDispatcher {
}

package art.gatoartstudio.synchronizedinstances.helpers;

import art.gatoartstudio.synchronizedinstances.SynchronizedInstances;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

import java.util.Arrays;
import java.util.List;

public class Log {
    private final static ComponentLogger logger = SynchronizedInstances.getInstance().getComponentLogger();

    /**
     * Logs an informational message to the console.
     *
     * @param message the informational message to be logged
     */
    public static void info( String message) {
        logger.info(
                Component.text(message)
                        .color(TextColor.color(0, 241, 255))
        );
    }

    /**
     * Logs a warning message to the console.
     *
     * @param message the warning message to be logged
     */
    public static void warning( String message) {
        logger.warn(
                Component.text(message)
        );
    }

    /**
     * Logs an error message to the console.
     *
     * @param message the error message to be logged
     */
    public static void error( String message) {
        logger.error(
                Component.text(message)
        );
    }

    /**
     * Logs an error message to the console.
     *
     * @param stackTrace the stack trace to be logged
     */
    public static void error( StackTraceElement[] stackTrace) {
        logger.error(
                String.join("\n", Arrays.stream(stackTrace).map(StackTraceElement::toString).toList())
        );
    }

    /**
     * Logs an error message to the console.
     *
     * @param e the exception to be logged
     */
    public static void error(Exception e) {
        Component logMessage = Component.text(e.getMessage() + "\n")
                .color(TextColor.color(255, 255, 0));

        List<TextComponent> stackTrace = Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).map(
                stackTraceElement -> Component.text(stackTraceElement + "\n")
                        .color(TextColor.color(255, 0, 0))
        ).toList();

        for (Component component : stackTrace) {
            logMessage = logMessage.append(component);
        }

        log(logMessage);
    }

    /**
     * Logs a debug message to the console.
     *
     * @param message the debug message to be logged
     */
    public static void debug(String message) {
        logger.debug(
                Component.text(message)
        );
    }

    /**
     * Logs a success message to the console.
     *
     * @param message the success message to be logged
     */
    public static void success(String message) {
        logger.info(
                Component.text(message)
                        .color(TextColor.color(47, 255, 0))
        );
    }

    /**
     * Logs a custom component message to the console.
     *
     * @param component the component message to be logged
     */
    public static void log(Component component) {
        logger.info(component);
    }
}

package org.fujion.webjar.validator;

import org.apache.commons.lang3.Validate;

import java.io.File;
import java.util.List;

/**
 * Base class for all rules.
 */
public abstract class AbstractRule {

    protected final File file;

    protected final List<String> args;

    /**
     * @param file File that is the target of the rule.
     * @param args Additional arguments for the rule.
     */
    public AbstractRule(File file, List<String> args) {
        this.args = args;
        this.file = file;
        Validate.isTrue(args.size() == getArgumentCount(), "Invalid number of arguments provided.");
    }

    /**
     * @return The number of arguments required by the rule.  Override if necessary.
     */
    public int getArgumentCount() {
        return 0;
    }

    /**
     * @return True if the rule logic succeeds.
     */
    protected abstract boolean passes();

}

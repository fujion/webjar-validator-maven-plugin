package org.fujion.webjar.validator;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang3.Validate;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;

/**
 * Validates that a directory contains a specified number of subdirectories.
 */
public class DirCountRule extends AbstractRule {

    public DirCountRule(File file, List<String> args) {
        super(file, args);
    }

    @Override
    public int getArgumentCount() {
        return 1;
    }

    @Override
    protected boolean passes() {
        int count = Integer.parseInt(args.get(0));
        Validate.isTrue(file.isDirectory(), "Argument is not a directory.");
        FileFilter filter = WildcardFileFilter.builder().setWildcards("*").get();
        File[] matches = file.listFiles(filter);
        return matches != null && count == Arrays.stream(matches).filter(File::isDirectory).count();
    }
}

package lab;


import lab.exceptions.FileAlreadyExistsException;

import java.io.*;
import java.util.logging.Logger;

class FileUtil {
    private Logger logger = Logger.getLogger("FileUtil");

    private File getInputFile(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        if (!file.exists() | !file.canRead()) {
            throw new FileNotFoundException(filePath);
        }
        return file;
    }

    private File getOutputFile(String filePath) throws FileAlreadyExistsException {
        File file = new File(filePath);
        if (file.exists()) {
            throw new FileAlreadyExistsException(filePath);
        }
        return file;
    }

    InputStream getInputStream(String filePath) throws IOException {
        File file = getInputFile(filePath);
        return new FileInputStream(file);
    }

    OutputStream getOutputStream(String filePath) throws IOException {
        File file = getOutputFile(filePath);
        return new FileOutputStream(file);
    }
}

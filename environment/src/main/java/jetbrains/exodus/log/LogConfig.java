/**
 * Copyright 2010 - 2019 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jetbrains.exodus.log;

import jetbrains.exodus.InvalidSettingException;
import jetbrains.exodus.core.dataStructures.Pair;
import jetbrains.exodus.crypto.StreamCipherProvider;
import jetbrains.exodus.env.EnvironmentConfig;
import jetbrains.exodus.io.DataReader;
import jetbrains.exodus.io.DataReaderWriterProvider;
import jetbrains.exodus.io.DataWriter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LogConfig {

    private static final int DEFAULT_FILE_SIZE = 1024; // in kilobytes

    private String location;
    private String readerWriterProvider;
    private DataReaderWriterProvider readerWriterProviderInstance;
    private long fileSize;
    private long lockTimeout;
    private long memoryUsage;
    private int memoryUsagePercentage;
    private DataReader reader;
    private DataWriter writer;
    private boolean isDurableWrite;
    private boolean sharedCache;
    private boolean nonBlockingCache;
    private int cacheGenerationCount;
    private int cacheReadAheadMultiple;
    private int cachePageSize;
    private int cacheOpenFilesCount;
    private boolean cleanDirectoryExpected;
    private boolean clearInvalidLog;
    private long syncPeriod;
    private boolean fullFileReadonly;
    private StreamCipherProvider cipherProvider;
    private byte[] cipherKey;
    private long cipherBasicIV;
    private boolean lockIgnored;

    public LogConfig() {
    }

    public LogConfig setLocation(@NotNull final String location) {
        this.location = location;
        return this;
    }

    public LogConfig setReaderWriterProvider(@NotNull final String provider) {
        readerWriterProvider = provider;
        return this;
    }

    public long getFileSize() {
        if (fileSize == 0) {
            fileSize = DEFAULT_FILE_SIZE;
        }
        return fileSize;
    }

    public LogConfig setFileSize(final long fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    public long getLockTimeout() {
        return lockTimeout;
    }

    public LogConfig setLockTimeout(long lockTimeout) {
        this.lockTimeout = lockTimeout;
        return this;
    }

    public boolean isLockIgnored() {
        return lockIgnored;
    }

    public void setLockIgnored(boolean lockIgnored) {
        this.lockIgnored = lockIgnored;
    }

    public long getMemoryUsage() {
        return memoryUsage;
    }

    public LogConfig setMemoryUsage(final long memUsage) {
        memoryUsage = memUsage;
        return this;
    }

    public int getMemoryUsagePercentage() {
        if (memoryUsagePercentage == 0) {
            memoryUsagePercentage = 50;
        }
        return memoryUsagePercentage;
    }

    public LogConfig setMemoryUsagePercentage(final int memoryUsagePercentage) {
        this.memoryUsagePercentage = memoryUsagePercentage;
        return this;
    }

    public DataReader getReader() {
        if (reader == null) {
            createReaderWriter();
        }
        return reader;
    }

    @Deprecated
    public LogConfig setReader(@NotNull final DataReader reader) {
        this.reader = reader;
        return this;
    }

    public DataWriter getWriter() {
        if (writer == null) {
            createReaderWriter();
        }
        return writer;
    }

    @Deprecated
    public LogConfig setWriter(@NotNull final DataWriter writer) {
        this.writer = writer;
        return this;
    }

    public LogConfig setReaderWriter(@NotNull final DataReader reader,
                                     @NotNull final DataWriter writer) {
        this.reader = reader;
        this.writer = writer;
        return this;
    }

    public boolean isDurableWrite() {
        return isDurableWrite;
    }

    public LogConfig setDurableWrite(boolean durableWrite) {
        isDurableWrite = durableWrite;
        return this;
    }

    public boolean isSharedCache() {
        return sharedCache;
    }

    public LogConfig setSharedCache(boolean sharedCache) {
        this.sharedCache = sharedCache;
        return this;
    }

    public boolean isNonBlockingCache() {
        return nonBlockingCache;
    }

    public LogConfig setNonBlockingCache(boolean nonBlockingCache) {
        this.nonBlockingCache = nonBlockingCache;
        return this;
    }

    public int getCacheGenerationCount() {
        if (cacheGenerationCount == 0) {
            cacheGenerationCount = EnvironmentConfig.DEFAULT.getLogCacheGenerationCount();
        }
        return cacheGenerationCount;
    }

    public LogConfig setCacheGenerationCount(int cacheGenerationCount) {
        this.cacheGenerationCount = cacheGenerationCount;
        return this;
    }

    public int getCacheReadAheadMultiple() {
        if (cacheReadAheadMultiple == 0) {
            cacheReadAheadMultiple = EnvironmentConfig.DEFAULT.getLogCacheReadAheadMultiple();
        }
        return cacheReadAheadMultiple;
    }

    public void setCacheReadAheadMultiple(int cacheReadAheadMultiple) {
        this.cacheReadAheadMultiple = cacheReadAheadMultiple;
    }

    public int getCachePageSize() {
        if (cachePageSize == 0) {
            cachePageSize = LogCache.MINIMUM_PAGE_SIZE;
        }
        return cachePageSize;
    }

    public LogConfig setCachePageSize(int cachePageSize) {
        this.cachePageSize = cachePageSize;
        return this;
    }

    public int getCacheOpenFilesCount() {
        if (cacheOpenFilesCount == 0) {
            cacheOpenFilesCount = LogCache.DEFAULT_OPEN_FILES_COUNT;
        }
        return cacheOpenFilesCount;
    }

    public LogConfig setCacheOpenFilesCount(int cacheOpenFilesCount) {
        this.cacheOpenFilesCount = cacheOpenFilesCount;
        return this;
    }

    public boolean isCleanDirectoryExpected() {
        return cleanDirectoryExpected;
    }

    public LogConfig setCleanDirectoryExpected(boolean cleanDirectoryExpected) {
        this.cleanDirectoryExpected = cleanDirectoryExpected;
        return this;
    }

    public boolean isClearInvalidLog() {
        return clearInvalidLog;
    }

    public LogConfig setClearInvalidLog(boolean clearInvalidLog) {
        this.clearInvalidLog = clearInvalidLog;
        return this;
    }

    public long getSyncPeriod() {
        if (syncPeriod == 0) {
            syncPeriod = EnvironmentConfig.DEFAULT.getLogSyncPeriod();
        }
        return syncPeriod;
    }

    public LogConfig setSyncPeriod(long syncPeriod) {
        this.syncPeriod = syncPeriod;
        return this;
    }

    public boolean isFullFileReadonly() {
        return fullFileReadonly;
    }

    public LogConfig setFullFileReadonly(boolean fullFileReadonly) {
        this.fullFileReadonly = fullFileReadonly;
        return this;
    }

    public StreamCipherProvider getCipherProvider() {
        return cipherProvider;
    }

    public LogConfig setCipherProvider(StreamCipherProvider cipherProvider) {
        this.cipherProvider = cipherProvider;
        return this;
    }

    public byte[] getCipherKey() {
        return cipherKey;
    }

    public LogConfig setCipherKey(byte[] cipherKey) {
        this.cipherKey = cipherKey;
        return this;
    }

    public long getCipherBasicIV() {
        return cipherBasicIV;
    }

    public LogConfig setCipherBasicIV(long basicIV) {
        this.cipherBasicIV = basicIV;
        return this;
    }

    public static LogConfig create(@NotNull final DataReader reader, @NotNull final DataWriter writer) {
        return new LogConfig().setReaderWriter(reader, writer);
    }

    @Nullable
    public DataReaderWriterProvider getReaderWriterProvider() {
        if (readerWriterProviderInstance == null && readerWriterProvider != null) {
            readerWriterProviderInstance = DataReaderWriterProvider.getProvider(readerWriterProvider);
            if (readerWriterProviderInstance == null) {
                throw new InvalidSettingException("Unknown DataReaderWriterProvider: " + readerWriterProvider);
            }
        }
        return readerWriterProviderInstance;
    }

    public boolean isReadonlyReaderWriterProvider() {
        final DataReaderWriterProvider provider = getReaderWriterProvider();
        return provider != null && provider.isReadonly();
    }

    private void createReaderWriter() {
        final String location = this.location;
        if (location == null) {
            throw new InvalidSettingException("Location for DataReader and DataWriter is not specified");
        }
        Pair<DataReader, DataWriter> readerWriter = getReaderWriterProvider().newReaderWriter(location);
        reader = readerWriter.getFirst();
        writer = readerWriter.getSecond();
    }
}

/**
 * Copyright 2010 - 2021 JetBrains s.r.o.
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
package jetbrains.exodus.util;

import java.io.ByteArrayInputStream;

public class ByteArraySizedInputStream extends ByteArrayInputStream {

    public ByteArraySizedInputStream(byte[] buf) {
        super(buf);
    }

    public ByteArraySizedInputStream(byte[] buf, int offset, int length) {
        super(buf, offset, length);
    }

    public int size() {
        return count - mark;
    }

    public int count() {
        return count;
    }

    public int pos() {
        return pos;
    }

    public void setPos(final int pos) {
        this.pos = pos;
    }

    public byte[] toByteArray() {
        return buf;
    }

    public ByteArraySizedInputStream copy() {
        return new ByteArraySizedInputStream(buf, pos, count);
    }
}

/*
 * Copyright ${inceptionYear} - ${year} ${owner}
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jetbrains.exodus.entitystore;

/**
 * Implementation of {@linkplain PersistentEntityStore} identifies each blob by a unique <i>blob handle</i> of type
 * {@code long}. {@code BlobHandleGenerator} defines a sequences of blob handles for newly created blobs.
 * Any inheritor of {@linkplain BlobVault} should implement {@linkplain #nextHandle()}.
 *
 * @see BlobVault
 * @see PersistentEntityStore
 */
public interface BlobHandleGenerator {

    /**
     * Generates next unique blob handle.
     *
     * @return next unique blob handle
     */
    long nextHandle();

    /**
     * BlobHandleGenerator for immutable {@linkplain BlobVault}s.
     */
    BlobHandleGenerator IMMUTABLE = () -> {
        throw new UnsupportedOperationException();
    };
}

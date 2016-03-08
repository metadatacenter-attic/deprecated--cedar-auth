package org.metadatacenter.server.security;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.IOException;

public interface IUserService<K, A, T> {
  public T findUserByApiKey(@NonNull A apiKey) throws IOException, ProcessingException;

}

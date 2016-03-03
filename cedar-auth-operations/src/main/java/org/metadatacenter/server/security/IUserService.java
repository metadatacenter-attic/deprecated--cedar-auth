package org.metadatacenter.server.security;

import checkers.nullness.quals.NonNull;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import org.metadatacenter.server.security.IUserService;

import java.io.IOException;

public interface IUserService<K, A, T> {
  public T findUserByApiKey(@NonNull A apiKey) throws IOException, ProcessingException;

}

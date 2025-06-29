package auth.application.create;

import entity.User;

public interface CreateUserUseCase {

    User createUser(final User user);

    User updateUser(final User user);
}

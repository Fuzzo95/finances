package auth.application.find;

import entity.User;

public interface FindUserUseCase {
    User findById(long id);
    User findByName(String name);
}

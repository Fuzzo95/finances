package auth.domain.model;

import java.util.List;

public record UserQuery(
        long id,
        String username,
        List<Long> roleIds,
        String password
) {
}

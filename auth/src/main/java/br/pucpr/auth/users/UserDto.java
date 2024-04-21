package br.pucpr.auth.users;

public record UserDto(
        Long id,
        String name,
        String email
) {
    public UserDto(User user) {
        this(user.getId(), user.getName(), user.getEmail());
    }
}

package services;

import config.Endpoints;
import io.restassured.response.Response;
import models.User;

public class UserService {
    private ApiClient apiClient = new ApiClient();

    public Response getUser(String userId) {
        return apiClient.get(Endpoints.GET_USER.replace("{id}", userId));
    }

    public Response createUser(User user) {
        return apiClient.post(Endpoints.CREATE_USER, user);
    }

    public Response deleteUser(String userId) {
        return apiClient.delete(Endpoints.DELETE_USER.replace("{id}", userId));
    }
}

package Service;

import dataaccess.AuthDataAccess;
import model.response.AuthResponse;

public class AuthService {
    private AuthDataAccess authDataAccess;

    public AuthService() {
        this.authDataAccess = new AuthDataAccess();
    }

    public AuthResponse validateAuth(String authToken) {
        if (authToken == null || authToken.isEmpty()) {
            return new AuthResponse(false, "Invalid authentication token.");
        }

        boolean isValid = authDataAccess.validateAuth(authToken);
        if (!isValid) {
            return new AuthResponse(false, "Authentication failed.");
        }

        return new AuthResponse(true, "Authentication successful.");
    }

    public AuthResponse logout(String authToken) {
        if (authToken == null || authToken.isEmpty()) {
            return new AuthResponse(false, "Invalid authentication token.");
        }

        boolean deletionSuccessful = authDataAccess.deleteAuth(authToken);
        if (deletionSuccessful) {
            return new AuthResponse(true, "Logout successful.");
        } else {
            return new AuthResponse(false, "Logout failed.");
        }
    }
}
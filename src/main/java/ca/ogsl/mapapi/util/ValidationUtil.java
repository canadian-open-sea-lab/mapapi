package ca.ogsl.mapapi.util;

import ca.ogsl.mapapi.errorhandling.AppException;

import javax.ws.rs.core.Response;

public class ValidationUtil {

    public static void validateAdminRole(String role) throws AppException {
        if (!role.equals(AppConstants.ADMIN_ROLE)) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 403,
                    "You do not have the required permission", AppConstants.PORTAL_URL);
        }
    }
}

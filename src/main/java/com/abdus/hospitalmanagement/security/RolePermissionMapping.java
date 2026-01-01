package com.abdus.hospitalmanagement.security;

import com.abdus.hospitalmanagement.entity.type.PermissionType;
import com.abdus.hospitalmanagement.entity.type.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.abdus.hospitalmanagement.entity.type.PermissionType.*;
import static com.abdus.hospitalmanagement.entity.type.Role.*;

public class RolePermissionMapping {
    private static final Map<Role, Set<PermissionType>>map = Map.of(
        PATIENT,Set.of(PATIENT_READ,APPOINTMENT_READ,APPOINTMENT_WRITE),
        DOCTOR,Set.of(APPOINTMENT_READ,APPOINTMENT_WRITE,APPOINTMENT_DELETE,PATIENT_READ),
        ADMIN,Set.of(APPOINTMENT_DELETE,APPOINTMENT_READ,APPOINTMENT_WRITE,PATIENT_READ,PATIENT_WRITE,USER_MANAGE,REPORT_VIEW)

    );

    public static Set<SimpleGrantedAuthority> getAuthoritiesForRole(Role role) {
         return map.get(role).stream()
                 .map(permissionType -> new SimpleGrantedAuthority(permissionType.getPermission()))
                 .collect(Collectors.toSet());
    }
}

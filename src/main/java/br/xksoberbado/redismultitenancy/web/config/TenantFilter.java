package br.xksoberbado.redismultitenancy.web.config;

import br.xksoberbado.redismultitenancy.config.TenantHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Objects;

@Component
public class TenantFilter extends OncePerRequestFilter {

    @Override
    @SneakyThrows
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) {
        var tenantId = request.getHeader("X-Tenant");

        if (Objects.isNull(tenantId) || tenantId.isBlank()) {
            tenantId = "default";
        }

        TenantHolder.set(tenantId);

        filterChain.doFilter(request, response);
    }
}

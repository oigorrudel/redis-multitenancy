package br.xksoberbado.redismultitenancy.config;

public final class TenantHolder {

    private static final ThreadLocal<String> TENANT = new ThreadLocal<>();

    public static String get() {
        return TENANT.get();
    }

    public static void set(final String tenant) {
        TENANT.set(tenant);
    }

    public static void clear() {
        TENANT.remove();
    }
}

import org.infinispan.Cache;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;


public class Setup {
    public static void main(String[] args) {
        GlobalConfigurationBuilder global = GlobalConfigurationBuilder.defaultClusteredBuilder();
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.clustering().cacheMode(CacheMode.DIST_SYNC);
        // Установка менеджера кеша по умолчанию
        DefaultCacheManager cacheManager = new DefaultCacheManager(global.build(), builder.build());
        // Инициализация диспетчера кеша
        Cache<Integer, String> cache = cacheManager.getCache();
        // Сохранять текущий адрес узла в ключах от 0 до 10
        for (int i = 0; i < 10; i++) {
            cache.put(i, cacheManager.getNodeAddress());
        }
        // Отображение текущего содержимого кэша для всего кластера
        cache.forEach((key, value) -> System.out.printf("%s = %s\n", key, value));

        //Остановка менеджера
        cacheManager.stop();
    }

}
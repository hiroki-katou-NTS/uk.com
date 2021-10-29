package nts.uk.shr.infra.file.storage.stream;

import lombok.Getter;
import nts.arc.system.ServerSystemProperties;
import nts.gul.error.FatalLog;
import nts.tenantlocator.client.TenantLocatorClient;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * ファイルストレージのパスを扱う
 * 必要に応じてTenantLocatorに問い合わせることも隠蔽
 */
public class FileStoragePath {

    /** true: テナントごとにストレージが異なるモード */
    private final boolean isTenantStoragesMode;

    /** 単一ストレージであればそのパスを持つ、テナントごとならばnull */
    @Getter
    private final Path singleStoragePath;

    public FileStoragePath() {
        String param = ServerSystemProperties.fileStoragePath();
        this.isTenantStoragesMode = "tenant".equals(param);
        this.singleStoragePath = this.isTenantStoragesMode ? null : new File(param).toPath();
    }

    public Path getPath(String tenantCode) {

        if (!isTenantStoragesMode) {
            return singleStoragePath;
        }

        Path path = getPathByTenant(tenantCode);
        if (path == null) {
            FatalLog.write(FileStoragePath.class, "テナント " + tenantCode + " のFileStorageが設定されていません。");
        }

        return path;
    }

    private final Map<String, Path> pathsByTenant = new HashMap<>();

    private Path getPathByTenant(String tenantCode) {

        return pathsByTenant.computeIfAbsent(
                tenantCode,
                tc -> TenantLocatorClient.getFileStorage(tc)
                        .map(f -> f.getStoragePath())
                        .map(p -> new File(p).toPath())
                        .orElse(null));
    }
}

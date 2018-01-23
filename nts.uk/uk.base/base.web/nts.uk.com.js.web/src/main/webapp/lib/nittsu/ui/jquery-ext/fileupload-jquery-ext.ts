/// <reference path="../../reference.ts"/>

interface JQuery {
    ntsFileUpload(options: nts.uk.ui.jqueryExtentions.ntsFileUpload.FileUploadOption);
}

module nts.uk.ui.jqueryExtentions {
    export module ntsFileUpload {
        export interface FileUploadOption {
            stereoType: string;
            onSuccess?(): any;
            onFail?(): any;
        }

        $.fn.ntsFileUpload = function(option: FileUploadOption) {
            let dfd = $.Deferred();
            let fileInput: HTMLElement;
            if ($(this).find("input[type='file']").length == 0) {
                fileInput = $(this).get(0);
            } else {
                fileInput = $(this).find("input[type='file']").get(0);
            }

            if (fileInput !== undefined) {
                let files: File[] = fileInput.files;
                if (files.length > 0) {
                    // Check file is deleted on Chrome
                    if (files[0].size == 0) {
                        dfd.reject({ message: nts.uk.resource.getMessage("Msg_158"), messageId: "Msg_158" });
                        return dfd.promise();
                    }
                    
                    var formData = new FormData();
                    formData.append("stereotype", option.stereoType);
                    formData.append("userfile", files[0]);
                    formData.append("filename", files[0].name);
                    nts.uk.request.uploadFile(formData, option).done(function(data, textStatus, jqXHR) {
                        // Business Exception
                        if (nts.uk.util.exception.isBusinessError(data)) {
                            if (option.onFail) option.onFail();
                            dfd.reject(data);
                        }
                        else {
                            if (option.onSuccess) option.onSuccess();
                            dfd.resolve(data);
                        }
                    }).fail(function(jqXHR, textStatus, errorThrown) {
                        
                        if (jqXHR.status === 413) {
                            dfd.reject({ message: "ファイルサイズが大きすぎます。", messageId: "0" });
                        } else {
                            // Client Exception
                            dfd.reject({ message: "アップロード処理に失敗しました。", messageId: "0" });
                        }
                    });
                }
                else {
                    dfd.reject({ message: "ファイルを選択してください。", messageId: "0" });
                }
            }
            else {
                dfd.reject({ messageId: "0", message: "ファイルを読み込めません。" });
            }
            return dfd.promise();
        }
    }
}
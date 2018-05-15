var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var jqueryExtentions;
            (function (jqueryExtentions) {
                var ntsFileUpload;
                (function (ntsFileUpload) {
                    $.fn.ntsFileUpload = function (option) {
                        var dfd = $.Deferred();
                        var fileInput;
                        if ($(this).find("input[type='file']").length == 0) {
                            fileInput = $(this).get(0);
                        }
                        else {
                            fileInput = $(this).find("input[type='file']").get(0);
                        }
                        if (fileInput !== undefined) {
                            var files = fileInput.files;
                            if (files.length > 0) {
                                if (files[0].size == 0) {
                                    dfd.reject({ message: nts.uk.resource.getMessage("Msg_158"), messageId: "Msg_158" });
                                    return dfd.promise();
                                }
                                var formData = new FormData();
                                formData.append("stereotype", option.stereoType);
                                formData.append("userfile", files[0]);
                                formData.append("filename", files[0].name);
                                nts.uk.request.uploadFile(formData, option).done(function (data, textStatus, jqXHR) {
                                    if (nts.uk.util.exception.isBusinessError(data)) {
                                        if (option.onFail)
                                            option.onFail();
                                        dfd.reject(data);
                                    }
                                    else {
                                        if (option.onSuccess)
                                            option.onSuccess();
                                        dfd.resolve(data);
                                    }
                                }).fail(function (jqXHR, textStatus, errorThrown) {
                                    if (jqXHR.status === 413) {
                                        dfd.reject({ message: "ファイルサイズが大きすぎます。", messageId: "0" });
                                    }
                                    else {
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
                    };
                })(ntsFileUpload = jqueryExtentions.ntsFileUpload || (jqueryExtentions.ntsFileUpload = {}));
            })(jqueryExtentions = ui.jqueryExtentions || (ui.jqueryExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=fileupload-jquery-ext.js.map
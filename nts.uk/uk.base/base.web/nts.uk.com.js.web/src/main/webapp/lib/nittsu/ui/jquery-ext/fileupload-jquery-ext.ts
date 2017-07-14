/// <reference path="../../reference.ts"/>

module nts.uk.ui.jqueryExtentions {
    module ntsFileUpload {
        export interface FileUploadOption {
            stereoType: string;
            onSuccess?(): any;
            onFail?(): any;
        }

        $.fn.ntsFileUpload = function(option: FileUploadOption) {
            let dfd = $.Deferred();
            let file: JQuery;
            if ($(this).find("input[type='file']").length == 0) {
                file = $(this)[0].files;
            } else {
                file = $(this).find("input[type='file']")[0].files;
            }
            if (file) {
                var formData = new FormData();
                formData.append("stereotype", option.stereoType);
                // HTML file input, chosen by user
                formData.append("userfile", file[0]);
                formData.append("filename", file[0].name);
                if (file[0]) {
                    return nts.uk.request.uploadFile(formData, option);
                } else {
                    dfd.reject({ message: "please select file", messageId: "-1" });
                    return dfd.promise();
                }
            } else {
                dfd.reject({ messageId: "0", message: "can not find control" });
            }
            return dfd.promise();
        }
    }
}
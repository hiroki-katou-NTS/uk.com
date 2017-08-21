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
            let fileInput: HTMLElement;
            if ($(this).find("input[type='file']").length == 0) {
                fileInput = $(this).get(0);
            } else {
                fileInput = $(this).find("input[type='file']").get(0);
            }

            if (fileInput !== undefined) {
                let file: File[] = fileInput.files;
                if (file.length > 0) {
                    var formData = new FormData();
                    formData.append("stereotype", option.stereoType);
                    formData.append("userfile", file[0]);
                    formData.append("filename", file[0].name);
                    return nts.uk.request.uploadFile(formData, option);
                } else {
                    dfd.reject({ message: "please select file", messageId: "-1" });
                }
            } else {
                dfd.reject({ messageId: "0", message: "can not find control" });
            }
            return dfd.promise();
        }
    }
}
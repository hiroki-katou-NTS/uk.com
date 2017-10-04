/// <reference path="../../reference.ts"/>

module nts.uk.ui.jqueryExtentions {
    module ntsImageEditor {

        $.fn.ntsImageEditor = function(method: string, option?: any) {
            let $element = $(this);
            switch(method){
                case "upload": {
                    return uploadImage($element, option);     
                } 
                case "selectByFileId": {
                    return downloadImage($element, option);     
                } 
                case "showByUrl": {
                    return viewByUrl($element, option);     
                } 
                default: 
                    return; 
            }            
        }

        function uploadImage($element: JQuery, option){
            let dfd = $.Deferred();
            
            let dataFile = $element.find(".image-preview").attr("src");
            if (!nts.uk.util.isNullOrUndefined(dataFile)) {
                
                let cropper = $element.data("cropper");
                let cropperData = cropper.getData(true);
                
                var formData = {
                        "fileName": $element.data("file-name"),
                        "stereoType": nts.uk.util.isNullOrUndefined(option) ? "image" : option.stereoType,
                        "file": dataFile,
                        "format": $element.data("file-type"),
                        "x": cropperData.x,
                        "y": cropperData.y,
                        "width": cropperData.width,
                        "height": cropperData.height 
                     };
                
                nts.uk.request.ajax("com", "image/editor/cropimage", formData).done(function(data, textStatus, jqXHR) {
                    if (data !== undefined && data.businessException) {
                        dfd.reject(data);
                    } else {
                        dfd.resolve(data);
                    }
                }).fail(function(jqXHR, textStatus, errorThrown) {
                    dfd.reject({ message: "Please check your network", messageId: "0" });
                });
            }
            else {
                dfd.reject({ message: "Please select file", messageId: "0" });
            }
            return dfd.promise();    
        }
        
        function downloadImage($element: JQuery, fileId){
            $element.trigger("srcchanging", {url: nts.uk.request.liveView(fileId), isOutSiteUrl: false});
        }
        
        function viewByUrl($element: JQuery, sourceUrl){
            $element.trigger("srcchanging", {url: sourceUrl, isOutSiteUrl: true});
            
        }
    }
}
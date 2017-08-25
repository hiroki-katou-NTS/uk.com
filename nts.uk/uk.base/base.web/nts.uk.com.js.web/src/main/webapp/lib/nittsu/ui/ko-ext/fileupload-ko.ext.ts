/// <reference path="../../reference.ts"/>

module nts.uk.ui.koExtentions {

    /**
     * CheckBox binding handler
     */
    class NtsFileUploadBindingHandler implements KnockoutBindingHandler {
        /**
         * Constructor..
         */
        constructor() {
        }

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            // Get data
            let data = valueAccessor();
            let fileName = data.filename;
            let onchange = (data.onchange !== undefined) ? data.onchange : $.noop;
            let onfilenameclick = (data.onfilenameclick !== undefined) ? data.onfilenameclick : $.noop;;
            
            // Container
            let container = $(element);
            let $fileuploadContainer = $("<div class='nts-fileupload-container'></div>");
            let $fileBrowserButton = $("<button class='browser-button'></button>");
            let $fileNameLable = $("<span class='filenamelabel' style='margin-left: 5px;'></span> ");
            let $fileInput = $("<input style='display:none;' type='file' class='fileinput'/>");
            $fileuploadContainer.append($fileBrowserButton);
            $fileuploadContainer.append($fileNameLable);
            $fileuploadContainer.append($fileInput);
            $fileuploadContainer.appendTo(container);
            $fileBrowserButton.click(function() {
//                $fileInput.val(null);
//                fileName("");
                $fileInput.click();
            });
            $fileInput.change(function() {
                var selectedFilePath = $(this).val();
                if (nts.uk.util.isNullOrEmpty(selectedFilePath)) {
                    if (nts.uk.util.isNullOrUndefined(container.data("file"))) {
                        this.files = (container.data("file"));        
                    }
                    return;    
                }
                container.data("file", this.files);
                var getSelectedFileName = selectedFilePath.substring(selectedFilePath.lastIndexOf("\\") + 1, selectedFilePath.length);
                container.data("file-name", getSelectedFileName);
                fileName(getSelectedFileName);
                onchange(getSelectedFileName);
            });
            $fileNameLable.click(function() {
                onfilenameclick($(this).text());
            });
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            let data = valueAccessor();
            let fileName = ko.unwrap(data.filename);
            let accept: string[] = (data.accept !== undefined) ? ko.unwrap(data.accept) : "";
            let asLink = (data.aslink !== undefined) ? ko.unwrap(data.aslink) : false;
            let text: string = (data.text !== undefined) ? nts.uk.resource.getText(ko.unwrap(data.text)) : "ファイルアップロード";
            let enable: boolean = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
            let container = $(element);
            container.find("input[type='file']").attr("accept", accept.toString());
            
            let $fileNameLable = container.find(".filenamelabel");
            if (container.data("file-name") !== fileName) {
                container.data( "file-name", "" );
                $fileNameLable.text("");
                container.find("input[type='file']").val(null);
                data.filename("");
            } else {
                $fileNameLable.text(fileName);    
            }   
            if (asLink == true) {
                $fileNameLable.addClass("hyperlink");
                $fileNameLable.removeClass("standard-file-name");
            } else {
                $fileNameLable.addClass("standard-file-name");
                $fileNameLable.removeClass("hyperlink");
            }
            
            let $fileBrowserButton = container.find(".browser-button");
            $fileBrowserButton.text(text);
            $fileBrowserButton.prop("disabled", !enable);
        }
    }


    ko.bindingHandlers['ntsFileUpload'] = new NtsFileUploadBindingHandler();
}
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
            let isLink = data.aslink
            let suportedExtension = ko.unwrap(data.accept);
            let textId: string = ko.unwrap(data.text);
            let control = $(element);
            let onchange = data.onchange;
            let onfilenameclick = data.onfilenameclick;
            let fileuploadContainer = $("<div class='nts-fileupload-container'></div>");
            let fileBrowserButton = $("<button class='browser-button' ></button>");
            let browserButtonText: string;

            if (textId) {
                browserButtonText = nts.uk.resource.getText(textId);
            } else {
                browserButtonText = "ファイルアップロード";
            }
            fileBrowserButton.text(browserButtonText);
            let fileNameLable = $("<span class='filenamelabel' style='margin-left: 5px;'></span> ");
            
            var displayAsLink=false;
            if(isLink != undefined){
                if(typeof isLink =='function'){
                    displayAsLink = isLink();    
                }else{
                    displayAsLink = isLink;
                }    
            }
            
            if (displayAsLink) {
                fileNameLable.addClass("filename");
            } else {
                fileNameLable.addClass("standard-file-name");
            }
            let fileInput = $("<input style ='display:none;' type='file' class='fileinput'/>");
            if (suportedExtension) {
                fileInput.attr("accept", suportedExtension.toString());
            }
            fileuploadContainer.append(fileBrowserButton);
            fileuploadContainer.append(fileNameLable);
            fileuploadContainer.append(fileInput);
            fileuploadContainer.appendTo(control);
            fileBrowserButton.click(function() {
                fileInput.val(null);
            });
            fileInput.change(function() {
                var selectedFilePath = $(this).val();
                var getSelectedFileName = selectedFilePath.substring(selectedFilePath.lastIndexOf("\\") + 1, selectedFilePath.length);
                if (fileName != undefined) {
                    data.filename(getSelectedFileName);
                }
                fileNameLable.text(getSelectedFileName);
                if (typeof onchange == 'function') {
                    onchange($(this).val());
                }
            });
            fileBrowserButton.click(function() {
                fileInput.click();
            });
            if (onfilenameclick) {
                fileNameLable.click(function() {
                    onfilenameclick($(this).text());
                });
            }

        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            let data = valueAccessor();
            let fileName = ko.unwrap(data.filename);
            let control = $(element);
            let fileNameLable = control.parent().find(".filenamelabel");
            fileNameLable.text(fileName);
        }
    }


    ko.bindingHandlers['ntsFileUpload'] = new NtsFileUploadBindingHandler();
}
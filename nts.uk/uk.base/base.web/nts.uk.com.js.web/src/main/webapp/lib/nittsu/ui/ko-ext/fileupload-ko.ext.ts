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
            let fileName: KnockoutObservable<string> = data.filename;
            let onchange: (filename: string) => void = (data.onchange !== undefined) ? data.onchange : $.noop;
            let onfilenameclick: (filename: string) => void = (data.onfilenameclick !== undefined) ? data.onfilenameclick : $.noop;
            let immediateUpload: boolean = data.immediateUpload === true;
            let uploadFinished: (fileInfo: any) => void = (data.uploadFinished !== undefined) ? data.uploadFinished : $.noop; 
            let stereoType: string = data.stereoType;
            
            // Container
            let container = $(element);
            let $fileuploadContainer = $("<div class='nts-fileupload-container cf'></div>");
            let $fileBrowserButton = $("<button class='browser-button'></button>");
            let $fileNameWrap = $("<span class='nts-editor-wrapped ntsControl'/>");
            let $fileNameInput = $("<input class='nts-editor nts-input' readonly='readonly'/>");
            let $fileNameLabel = $("<span class='filenamelabel hyperlink'></span> ");
            let $fileInput = $("<input type='file' class='fileinput'/>");
            
            $fileuploadContainer.append($fileBrowserButton);
            $fileNameWrap.append($fileNameInput);
            $fileuploadContainer.append($fileNameWrap);
            $fileuploadContainer.append($fileNameLabel);
            $fileuploadContainer.append($fileInput);
            $fileuploadContainer.appendTo(container);

            $fileBrowserButton.attr("tabindex", -1).click(function() {
                $fileInput.click();
            });

            $fileInput.change(function() {
                var selectedFilePath = $(this).val();
                if (nts.uk.util.isNullOrEmpty(selectedFilePath)) {
                    if (!nts.uk.util.isNullOrUndefined(container.data("file"))) {
                        this.files = (container.data("file"));        
                    }
                    return;    
                }
                container.data("file", this.files);
                var getSelectedFileName = selectedFilePath.substring(selectedFilePath.lastIndexOf("\\") + 1, selectedFilePath.length);
                container.data("file-name", getSelectedFileName);
                fileName(getSelectedFileName);
                onchange(getSelectedFileName);
                
                nts.uk.ui.block.grayout();
                $fileInput.ntsFileUpload({ stereoType: stereoType })
                    .done(data => {
                        uploadFinished(data[0]);
                    })
                    .fail(data => {
                        nts.uk.ui.dialog.alertError(data);
                    })
                    .always(() => {
                        nts.uk.ui.block.clear();
                    });
            });
            
            $fileNameLabel.click(function() {
                onfilenameclick($(this).text());
            });
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            let data = valueAccessor();
            let fileName: string = ko.unwrap(data.filename);
            let accept: string[] = (data.accept !== undefined) ? ko.unwrap(data.accept) : "";
            let asLink = (data.aslink !== undefined) ? ko.unwrap(data.aslink) : false;
            let text: string = (data.text !== undefined) ? nts.uk.resource.getText(ko.unwrap(data.text)) : "参照";
            let enable: boolean = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
            
            let container = $(element);
            container.find("input[type='file']").attr("accept", accept.toString());
            let $fileNameWrap = container.find(".nts-editor-wrapped");
            let $fileNameInput = container.find(".nts-input");
            let $fileNameLabel = container.find(".filenamelabel");
            
            if (container.data("file-name") !== fileName) {
                container.data( "file-name", "" );
                $fileNameInput.val("");
                $fileNameLabel.text("");
                container.find("input[type='file']").val(null);
                data.filename("");
            } else {
                $fileNameLabel.text(fileName);   
                $fileNameInput.val(fileName); 
            }   
            if (asLink == true) {
                $fileNameLabel.removeClass("hidden");
                $fileNameWrap.addClass("hidden");
            } else {
                $fileNameLabel.addClass("hidden");
                $fileNameWrap.removeClass("hidden");
            }
            
            let $fileBrowserButton = container.find(".browser-button");
            $fileBrowserButton.text(text);
            $fileBrowserButton.prop("disabled", !enable);
            $fileNameInput.prop("disabled", !enable);
        }
    }


    ko.bindingHandlers['ntsFileUpload'] = new NtsFileUploadBindingHandler();
}
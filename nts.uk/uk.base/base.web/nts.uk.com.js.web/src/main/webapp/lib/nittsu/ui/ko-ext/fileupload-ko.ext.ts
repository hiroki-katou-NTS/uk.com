/// <reference path="../../reference.ts"/>

module nts.uk.ui.koExtentions {
    
    const FILES_CACHE_FOR_CANCEL = "files-cache-for-cancel";
    const IS_RESTORED_BY_CANCEL = "restored-by-cancel";
    const SELECTED_FILE_NAME = "selected-file-name";
    const STEREOTYPE = "stereotype";
    const IMMEDIATE_UPLOAD = "immediate-upload";

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
            let uploadFinished: (fileInfo: any) => void = (data.uploadFinished !== undefined) ? data.uploadFinished : $.noop;
            
            // Container
            let $container = $(element)
            
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
            $fileuploadContainer.appendTo($container);

            $fileBrowserButton.attr("tabindex", -1).click(function() {
                $fileInput.click();
            });
            
            $fileInput.change(function() {
                if ($container.data(IS_RESTORED_BY_CANCEL) === true) {
                    $container.data(IS_RESTORED_BY_CANCEL, false)
                    return;
                }
                
                var selectedFilePath = $(this).val();
                
                // canceled on selecting file dialog
                if (nts.uk.util.isNullOrEmpty(selectedFilePath)) {
                    if (!nts.uk.util.isNullOrUndefined($container.data(FILES_CACHE_FOR_CANCEL))) {
                        $container.data(IS_RESTORED_BY_CANCEL, true);
                        this.files = ($container.data(FILES_CACHE_FOR_CANCEL));
                    }
                    return;
                }
                
                $container.data(FILES_CACHE_FOR_CANCEL, this.files);
                
                var selectedFileName = selectedFilePath.substring(selectedFilePath.lastIndexOf("\\") + 1, selectedFilePath.length);
                $container.data(SELECTED_FILE_NAME, selectedFileName);
                fileName(selectedFileName);
                onchange(selectedFileName);
                
                if ($container.data(IMMEDIATE_UPLOAD)) {
                    nts.uk.ui.block.grayout();
                    $fileInput.ntsFileUpload({ stereoType: $container.data(STEREOTYPE) })
                        .done(data => {
                            uploadFinished.call(bindingContext.$data, data[0]);
                        })
                        .fail(data => {
                            nts.uk.ui.dialog.alertError(data);
                        })
                        .always(() => {
                            nts.uk.ui.block.clear();
                        });
                }
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
            
            let $container = $(element)
                .data(STEREOTYPE, ko.unwrap(data.stereoType))
                .data(IMMEDIATE_UPLOAD, ko.unwrap(data.immediateUpload) === true);
            
            $container.find("input[type='file']").attr("accept", accept.toString());
            let $fileNameWrap = $container.find(".nts-editor-wrapped");
            let $fileNameInput = $container.find(".nts-input");
            let $fileNameLabel = $container.find(".filenamelabel");
            
            // when change just only filename, file in input must be cleared
            if ($container.data(SELECTED_FILE_NAME) !== fileName) {
                $container.data(SELECTED_FILE_NAME, "");
                $container.find("input[type='file']").val(null);
            }
            
            $fileNameInput.val(fileName);
            $fileNameLabel.text(fileName);

            if (asLink == true) {
                $fileNameLabel.removeClass("hidden");
                $fileNameWrap.addClass("hidden");
            } else {
                $fileNameLabel.addClass("hidden");
                $fileNameWrap.removeClass("hidden");
            }
            
            let $fileBrowserButton = $container.find(".browser-button");
            $fileBrowserButton.text(text);
            $fileBrowserButton.prop("disabled", !enable);
            $fileNameInput.prop("disabled", !enable);
        }
    }


    ko.bindingHandlers['ntsFileUpload'] = new NtsFileUploadBindingHandler();
}
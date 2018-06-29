var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                var CONTROL_NAME = "control-name";
                var REQUIRED = "required";
                var FILES_CACHE_FOR_CANCEL = "files-cache-for-cancel";
                var IS_RESTORED_BY_CANCEL = "restored-by-cancel";
                var SELECTED_FILE_NAME = "selected-file-name";
                var STEREOTYPE = "stereotype";
                var IMMEDIATE_UPLOAD = "immediate-upload";
                var NtsFileUploadBindingHandler = (function () {
                    function NtsFileUploadBindingHandler() {
                    }
                    NtsFileUploadBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var fileName = data.filename;
                        var onchange = (data.onchange !== undefined) ? data.onchange : $.noop;
                        var onfilenameclick = (data.onfilenameclick !== undefined) ? data.onfilenameclick : $.noop;
                        var uploadFinished = (data.uploadFinished !== undefined) ? data.uploadFinished : $.noop;
                        var $container = $(element);
                        var $fileuploadContainer = $("<div class='nts-fileupload-container cf'></div>");
                        var $fileBrowserButton = $("<button class='browser-button'></button>");
                        var $fileNameWrap = $("<span class='nts-editor-wrapped ntsControl'/>");
                        var $fileNameInput = $("<input class='nts-editor nts-input' readonly='readonly' tabindex='-1'/>");
                        var $fileNameLabel = $("<span class='filenamelabel hyperlink'></span> ");
                        var $fileInput = $("<input type='file' class='fileinput'/>");
                        $fileuploadContainer.append($fileBrowserButton);
                        $fileNameWrap.append($fileNameInput);
                        $fileuploadContainer.append($fileNameWrap);
                        $fileuploadContainer.append($fileNameLabel);
                        $fileuploadContainer.append($fileInput);
                        $fileuploadContainer.appendTo($container);
                        $fileBrowserButton.attr("tabindex", -1).click(function () {
                            $fileInput.click();
                        });
                        $fileInput.change(function () {
                            if ($container.data(IS_RESTORED_BY_CANCEL) === true) {
                                $container.data(IS_RESTORED_BY_CANCEL, false);
                                return;
                            }
                            $container.ntsError("clear");
                            var selectedFilePath = $(this).val();
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
                                    .done(function (data) {
                                    uploadFinished.call(bindingContext.$data, data[0]);
                                })
                                    .fail(function (data) {
                                    nts.uk.ui.dialog.alertError(data);
                                })
                                    .always(function () {
                                    nts.uk.ui.block.clear();
                                });
                            }
                        });
                        $fileNameLabel.click(function () {
                            onfilenameclick($(this).text());
                        });
                        $container.bind("validate", function () {
                            if ($container.data(REQUIRED) && uk.util.isNullOrEmpty(ko.unwrap(data.filename))) {
                                var controlName = $container.data(CONTROL_NAME);
                                $container.ntsError("set", uk.resource.getMessage("FND_E_REQ_SELECT", [controlName]), "FND_E_REQ_SELECT");
                            }
                            else {
                                $container.ntsError("clear");
                            }
                        });
                        $container
                            .data(ui.DATA_SET_ERROR_STYLE, function () {
                            $container.addClass("error");
                        })
                            .data(ui.DATA_CLEAR_ERROR_STYLE, function () {
                            $container.removeClass("error");
                        });
                    };
                    NtsFileUploadBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var fileName = ko.unwrap(data.filename);
                        var accept = (data.accept !== undefined) ? ko.unwrap(data.accept) : "";
                        var asLink = (data.aslink !== undefined) ? ko.unwrap(data.aslink) : false;
                        var text = (data.text !== undefined) ? nts.uk.resource.getText(ko.unwrap(data.text)) : "参照";
                        var enable = (data.enable !== undefined) ? ko.unwrap(data.enable) : true;
                        var $container = $(element)
                            .data(CONTROL_NAME, ko.unwrap(data.name))
                            .data(REQUIRED, ko.unwrap(data.required) === true)
                            .data(STEREOTYPE, ko.unwrap(data.stereoType))
                            .data(IMMEDIATE_UPLOAD, ko.unwrap(data.immediateUpload) === true);
                        $container.find("input[type='file']").attr("accept", accept.toString());
                        var $fileNameWrap = $container.find(".nts-editor-wrapped");
                        var $fileNameInput = $container.find(".nts-input");
                        var $fileNameLabel = $container.find(".filenamelabel");
                        if ($container.data(SELECTED_FILE_NAME) !== fileName) {
                            $container.data(SELECTED_FILE_NAME, "");
                            $container.find("input[type='file']").val(null);
                        }
                        $fileNameInput.val(fileName);
                        $fileNameLabel.text(fileName);
                        if (asLink == true) {
                            $fileNameLabel.removeClass("hidden");
                            $fileNameWrap.addClass("hidden");
                        }
                        else {
                            $fileNameLabel.addClass("hidden");
                            $fileNameWrap.removeClass("hidden");
                        }
                        var $fileBrowserButton = $container.find(".browser-button");
                        $fileBrowserButton.text(text);
                        $fileBrowserButton.prop("disabled", !enable);
                        $fileNameInput.prop("disabled", !enable);
                    };
                    return NtsFileUploadBindingHandler;
                }());
                ko.bindingHandlers['ntsFileUpload'] = new NtsFileUploadBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=fileupload-ko.ext.js.map
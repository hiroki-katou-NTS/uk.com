/// <reference path="../../reference.ts"/>
var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui) {
            var koExtentions;
            (function (koExtentions) {
                /**
                 * CheckBox binding handler
                 */
                var NtsFileUploadBindingHandler = (function () {
                    /**
                     * Constructor..
                     */
                    function NtsFileUploadBindingHandler() {
                    }
                    /**
                     * Init.
                     */
                    NtsFileUploadBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        // Get data
                        var data = valueAccessor();
                        var fileName = data.filename;
                        var suportedExtension = ko.unwrap(data.accept);
                        var textId = ko.unwrap(data.text);
                        var control = $(element);
                        var onchange = data.onchange;
                        var onfilenameclick = data.onfilenameclick;
                        var fileuploadContainer = $("<div class='nts-fileupload-container'></div>");
                        var fileBrowserButton = $("<button class='browser-button' ></button>");
                        var browserButtonText;
                        if (textId) {
                            browserButtonText = nts.uk.resource.getText(textId);
                        }
                        else {
                            browserButtonText = "ファイルアップロード";
                        }
                        fileBrowserButton.text(browserButtonText);
                        var fileNameLable = $("<span class='filename'></span> ");
                        var fileInput = $("<input style ='display:none' type='file' class='fileinput'/>");
                        if (suportedExtension) {
                            fileInput.attr("accept", suportedExtension.toString());
                        }
                        fileuploadContainer.append(fileBrowserButton);
                        fileuploadContainer.append(fileNameLable);
                        fileuploadContainer.append(fileInput);
                        fileuploadContainer.appendTo(control);
                        fileInput.change(function () {
                            if (fileName != undefined) {
                                data.filename($(this).val());
                            }
                            else {
                                fileNameLable.text($(this).val());
                            }
                            if (typeof onchange == 'function') {
                                onchange($(this).val());
                            }
                        });
                        fileBrowserButton.click(function () {
                            fileInput.click();
                        });
                        if (onfilenameclick) {
                            fileNameLable.click(function () {
                                onfilenameclick($(this).text());
                            });
                        }
                    };
                    /**
                     * Update
                     */
                    NtsFileUploadBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var fileName = ko.unwrap(data.filename);
                        var control = $(element);
                        var fileNameLable = control.parent().find(".filename");
                        fileNameLable.text(fileName);
                    };
                    return NtsFileUploadBindingHandler;
                }());
                ko.bindingHandlers['ntsFileUpload'] = new NtsFileUploadBindingHandler();
            })(koExtentions = ui.koExtentions || (ui.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=fileupload-ko.ext.js.map
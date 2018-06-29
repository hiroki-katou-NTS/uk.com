var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var ui;
        (function (ui_1) {
            var koExtentions;
            (function (koExtentions) {
                var NtsImageEditorBindingHandler = (function () {
                    function NtsImageEditorBindingHandler() {
                    }
                    NtsImageEditorBindingHandler.prototype.init = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor();
                        var editable = nts.uk.util.isNullOrUndefined(data.editable) ? false : ko.unwrap(data.editable);
                        var zoomble = nts.uk.util.isNullOrUndefined(data.zoomble) ? false : ko.unwrap(data.zoomble);
                        var width = nts.uk.util.isNullOrUndefined(data.width) ? 600 : ko.unwrap(data.width);
                        var freeResize = nts.uk.util.isNullOrUndefined(data.freeResize) ? true : ko.unwrap(data.freeResize);
                        var resizeRatio = nts.uk.util.isNullOrUndefined(data.resizeRatio) ? 1 : ko.unwrap(data.resizeRatio);
                        var height = nts.uk.util.isNullOrUndefined(data.height) ? 600 : ko.unwrap(data.height);
                        var extension = nts.uk.util.isNullOrUndefined(data.accept) ? [] : ko.unwrap(data.accept);
                        var msgIdForUnknownFile = nts.uk.util.isNullOrUndefined(data.msgIdForUnknownFile) ? 'Msg_77' : ko.unwrap(data.msgIdForUnknownFile);
                        var croppable = false;
                        var helper = new ImageEditorHelper(extension, msgIdForUnknownFile);
                        var $container = $("<div>", { 'class': 'image-editor-container' }), $element = $(element).append($container);
                        var constructSite = new ImageEditorConstructSite($element, helper);
                        var $uploadArea = $("<div>", { "class": "image-upload-container image-editor-area cf" });
                        $container.append($uploadArea);
                        if (editable === true) {
                            croppable = true;
                            var confirm_1 = { checked: ko.observable(true) };
                            $(element).data('checkbox', confirm_1);
                            var $editContainer = $("<div>", { "class": "edit-action-container image-editor-area" });
                            $container.append($editContainer);
                            constructSite.buildCheckBoxArea(allBindingsAccessor, viewModel, bindingContext);
                        }
                        constructSite.buildActionArea();
                        constructSite.buildUploadAction();
                        constructSite.buildImagePreviewArea();
                        constructSite.buildFileChangeHandler();
                        var customOption = {
                            aspectRatio: freeResize ? 0 : resizeRatio,
                            dragMode: croppable ? "crop" : "none",
                            modal: false
                        };
                        constructSite.buildImageLoadedHandler(zoomble, customOption);
                        constructSite.buildSrcChangeHandler();
                        constructSite.buildImageDropEvent();
                        $element.find(".image-holder").width(width - 12).height(height - 12);
                        return { 'controlsDescendantBindings': true };
                    };
                    NtsImageEditorBindingHandler.prototype.update = function (element, valueAccessor, allBindingsAccessor, viewModel, bindingContext) {
                        var data = valueAccessor(), $element = $(element), confirm = $(element).data('checkbox'), $checkbox = $element.find('.comfirm-checkbox');
                        if (!nts.uk.util.isNullOrEmpty($checkbox)) {
                            ko.bindingHandlers["ntsCheckBox"].update($checkbox[0], function () {
                                return confirm;
                            }, allBindingsAccessor, viewModel, bindingContext);
                        }
                    };
                    return NtsImageEditorBindingHandler;
                }());
                var ImageEditorConstructSite = (function () {
                    function ImageEditorConstructSite($root, helper) {
                        this.$root = $root;
                        this.helper = helper;
                    }
                    ImageEditorConstructSite.prototype.buildCheckBoxArea = function (allBindingsAccessor, viewModel, bindingContext) {
                        var self = this;
                        var $checkboxHolder = $("<div>", { "class": "checkbox-holder image-editor-component" });
                        var $editContainer = this.$root.find(".edit-action-container");
                        $editContainer.append($checkboxHolder);
                        this.$checkbox = $("<div>", { "class": "comfirm-checkbox style-button", text: "表示エリア選択する" });
                        var $comment = $("<div>", { "class": "crop-description cf" });
                        $checkboxHolder.append(this.$checkbox);
                        $checkboxHolder.append($comment);
                        var $cropAreaIcon = $("<div>", { "class": "crop-icon inline-container" });
                        var $cropText = $("<div>", { "class": "crop-description-text inline-container" });
                        var $mousePointerIcon = $("<div>", { "class": "mouse-icon inline-container" });
                        var $mouseText = $("<div>", { "class": "mouse-description-text inline-container" });
                        $("<label>", { "class": "info-label", "text": "のエリア内をメイン画面に表示します。" }).appendTo($cropText);
                        $("<label>", { "class": "info-label", "text": "マウスのドラッグ＆ドロップでエリアを変更できます。" }).appendTo($mouseText);
                        $comment.append($cropAreaIcon).append($cropText).append($mousePointerIcon).append($mouseText);
                        var checkboxId = nts.uk.util.randomId();
                        ko.bindingHandlers["ntsCheckBox"].init(this.$checkbox[0], function () {
                            return self.$root.data('checkbox');
                        }, allBindingsAccessor, viewModel, bindingContext);
                    };
                    ImageEditorConstructSite.prototype.buildActionArea = function () {
                        this.$inputFile = $("<input>", { "class": "fileinput", "type": "file", "accept": this.helper.toStringExtension() })
                            .appendTo($("<div>", { "class": "image-editor-component inline-container nts-fileupload-container" }));
                        this.$imageNameLbl = $("<label>", { "class": "image-name-lbl info-label" })
                            .appendTo($("<div>", { "class": "image-editor-component inline-container" }));
                        this.$imageSizeLbl = $("<label>", { "class": "image-info-lbl info-label" })
                            .appendTo(this.$imageNameLbl.parent());
                        this.$uploadBtn = $("<button>", { "class": "upload-btn" })
                            .appendTo($("<div>", { "class": "image-editor-component inline-container" }));
                        var $uploadArea = this.$root.find(".image-upload-container");
                        $uploadArea.append(this.$uploadBtn.parent());
                        $uploadArea.append(this.$imageNameLbl.parent());
                        $uploadArea.append(this.$inputFile.parent());
                    };
                    ImageEditorConstructSite.prototype.buildImagePreviewArea = function () {
                        this.$previewArea = $("<div>", { "class": "image-preview-container image-editor-area" });
                        this.$previewArea.appendTo(this.$root.find(".image-editor-container"));
                        var imagePreviewId = nts.uk.util.randomId();
                        var $imageContainer = $("<div>", { "class": "image-container container-no-upload-background" }).appendTo(this.$previewArea);
                        var $imageHolder = $("<div>", { "class": "image-holder image-editor-component image-upload-icon" }).appendTo($imageContainer);
                        this.$imagePreview = $("<img>", { "class": "image-preview", "id": imagePreviewId }).appendTo($imageHolder);
                    };
                    ImageEditorConstructSite.prototype.buildUploadAction = function () {
                        var self = this;
                        self.$uploadBtn.text("参照").click(function (evt) {
                            self.$inputFile.click();
                        });
                    };
                    ImageEditorConstructSite.prototype.buildImageDropEvent = function () {
                        var self = this;
                        self.$previewArea.on('drop dragdrop', function (evt, ui) {
                            event.preventDefault();
                            var files = evt.originalEvent["dataTransfer"].files;
                            if (!nts.uk.util.isNullOrEmpty(files)) {
                                self.validateFile(files);
                            }
                        });
                        this.$previewArea.on('dragenter', function (event) {
                            event.preventDefault();
                        });
                        this.$previewArea.on('dragleave', function (evt, ui) {
                        });
                        this.$previewArea.on('dragover', function (event) {
                            event.preventDefault();
                        });
                    };
                    ImageEditorConstructSite.prototype.buildImageLoadedHandler = function (zoomble, customOption) {
                        var self = this;
                        self.$root.data("img-status", self.buildImgStatus("not init", 0));
                        self.$imagePreview.on('load', function () {
                            var image = new Image();
                            image.src = self.$imagePreview.attr("src");
                            image.onload = function () {
                                self.$imageSizeLbl.text("　(大きさ " + this.height + "x" + this.width + "　　サイズ " + self.helper.getFileSize(self.$root.data("size")) + ")");
                                if (!nts.uk.util.isNullOrUndefined(self.cropper)) {
                                    self.cropper.destroy();
                                }
                                self.$root.data("original-img", image.src);
                                var option = {
                                    viewMode: 1,
                                    guides: false,
                                    autoCrop: false,
                                    highlight: false,
                                    zoomable: zoomble,
                                    crop: function (e) {
                                    }, cropstart: function (e) {
                                    }
                                };
                                jQuery.extend(option, customOption);
                                self.cropper = new Cropper(self.$imagePreview[0], option);
                                self.$root.data("cropper", self.cropper);
                                self.$root.data("img-status", self.buildImgStatus("loaded", 4));
                                var evtData = {
                                    size: self.$root.data("size"),
                                    height: this.height,
                                    width: this.width,
                                    name: self.$root.data("file-name"),
                                    fileType: self.$root.data("file-type")
                                };
                                self.$root.trigger("imgloaded", evtData);
                            };
                        }).on("error", function () {
                            self.$root.data("img-status", self.buildImgStatus("load fail", 3));
                        });
                    };
                    ImageEditorConstructSite.prototype.buildImgStatus = function (status, statusCode) {
                        return {
                            imgOnView: statusCode === 4 ? true : false,
                            imgStatus: status,
                            imgStatusCode: statusCode
                        };
                    };
                    ImageEditorConstructSite.prototype.buildSrcChangeHandler = function () {
                        var self = this;
                        self.$root.bind("srcchanging", function (evt, query) {
                            self.$root.data("img-status", self.buildImgStatus("img loading", 2));
                            var target = self.helper.getUrl(query);
                            var xhr = self.getXRequest();
                            if (xhr === null) {
                                self.destroyImg(query);
                                return;
                            }
                            xhr.open('GET', target);
                            xhr.responseType = 'blob';
                            xhr.onload = function (e) {
                                if (this.status == 200) {
                                    if (xhr.response.type.indexOf("image") >= 0) {
                                        var reader = new FileReader();
                                        reader.readAsDataURL(xhr.response);
                                        reader.onload = function () {
                                            self.helper.getFileNameFromUrl().done(function (fileName) {
                                                var fileType = xhr.response.type.split("/")[1], fileName = self.helper.data.isOutSiteUrl ? (fileName + "." + fileType) : fileName;
                                                self.backupData(null, fileName, fileType, xhr.response.size);
                                                self.$imagePreview.attr("src", reader.result);
                                                self.$imagePreview.closest(".image-holder").removeClass(".image-upload-icon");
                                                self.$imagePreview.closest(".image-container").removeClass(".container-no-upload-background");
                                            });
                                        };
                                    }
                                    else {
                                        self.destroyImg(query);
                                    }
                                }
                                else {
                                    self.destroyImg(query);
                                }
                            };
                            xhr.send();
                        });
                    };
                    ImageEditorConstructSite.prototype.destroyImg = function (query) {
                        var self = this;
                        nts.uk.ui.dialog.alert("画像データが正しくないです。。").then(function () {
                            self.$root.data("img-status", self.buildImgStatus("load fail", 3));
                            self.backupData(null, "", "", 0);
                            self.$imagePreview.attr("src", "");
                            self.$imagePreview.closest(".image-holder").addClass(".image-upload-icon");
                            self.$imagePreview.closest(".image-container").addClass(".container-no-upload-background");
                            self.$imageSizeLbl.text("");
                            if (!nts.uk.util.isNullOrUndefined(self.cropper)) {
                                self.cropper.destroy();
                            }
                            self.$root.data("cropper", self.cropper);
                            query.actionOnClose();
                        });
                    };
                    ImageEditorConstructSite.prototype.getXRequest = function () {
                        return new XMLHttpRequest();
                    };
                    ImageEditorConstructSite.prototype.buildFileChangeHandler = function () {
                        var self = this;
                        self.$inputFile.change(function () {
                            self.$root.data("img-status", self.buildImgStatus("img loading", 2));
                            if (nts.uk.util.isNullOrEmpty(this.files)) {
                                self.$root.data("img-status", self.buildImgStatus("load fail", 3));
                                return;
                            }
                            self.validateFile(this.files);
                        });
                    };
                    ImageEditorConstructSite.prototype.validateFile = function (files) {
                        var self = this;
                        var firstImageFile = self.helper.getFirstFile(files);
                        if (!nts.uk.util.isNullOrUndefined(firstImageFile)) {
                            self.assignImageToView(firstImageFile);
                        }
                        else {
                            nts.uk.ui.dialog.alertError({ messageId: self.helper.getMsgIdForUnknownFile(), messageParams: [self.helper.toStringExtension()] });
                        }
                    };
                    ImageEditorConstructSite.prototype.assignImageToView = function (file) {
                        var self = this;
                        if (FileReader && file) {
                            var fr = new FileReader();
                            fr.onload = function () {
                                self.$imagePreview.attr("src", fr.result);
                                self.backupData(file, file.name, file.type.split("/")[1], file.size);
                            };
                            fr.onerror = function () {
                                self.destroyImg({ actionOnClose: $.noop });
                            };
                            fr.readAsDataURL(file);
                        }
                    };
                    ImageEditorConstructSite.prototype.backupData = function (file, name, format, size) {
                        var self = this;
                        self.$root.data("file", file);
                        self.$root.data("file-name", name);
                        self.$root.data("file-type", format);
                        self.$root.data("size", size);
                        self.$imageNameLbl.text(name);
                    };
                    return ImageEditorConstructSite;
                }());
                var ImageEditorHelper = (function () {
                    function ImageEditorHelper(extensions, msgIdForUnknownFile, query) {
                        this.IMAGE_EXTENSION = [".png", ".PNG", ".jpg", ".JPG", ".JPEG", ".jpeg"];
                        this.BYTE_SIZE = 1024;
                        this.SIZE_UNITS = ["BYTE", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"];
                        var self = this;
                        self.data = query;
                        self.msgIdForUnknownFile = msgIdForUnknownFile;
                        if (!nts.uk.util.isNullOrEmpty(extensions)) {
                            self.IMAGE_EXTENSION = [];
                            _.forEach(extensions, function (ex) {
                                self.IMAGE_EXTENSION.push(ex.toLowerCase());
                                self.IMAGE_EXTENSION.push(ex.toUpperCase());
                            });
                        }
                    }
                    ImageEditorHelper.prototype.toStringExtension = function () {
                        return this.IMAGE_EXTENSION.join(", ");
                    };
                    ImageEditorHelper.prototype.getMsgIdForUnknownFile = function () {
                        return this.msgIdForUnknownFile;
                    };
                    ImageEditorHelper.prototype.getFirstFile = function (files) {
                        var IMAGE_EXTENSION = this.IMAGE_EXTENSION;
                        return _.find(files, function (file) {
                            return _.find(IMAGE_EXTENSION, function (ie) {
                                var isType = file.type === ie.replace(".", "");
                                var isType2 = file.name.substr(file.name.lastIndexOf(".")) === ie;
                                return isType || isType2;
                            }) !== undefined;
                        });
                    };
                    ImageEditorHelper.prototype.getFileSize = function (originalSize) {
                        var i = 0, result = originalSize;
                        while (result > 5 * this.BYTE_SIZE) {
                            result = result / this.BYTE_SIZE;
                            i++;
                        }
                        var idx = i < this.SIZE_UNITS.length ? i : this.SIZE_UNITS.length - 1;
                        return uk.ntsNumber.trunc(result) + this.SIZE_UNITS[idx];
                    };
                    ImageEditorHelper.prototype.getUrl = function (query) {
                        if (!nts.uk.util.isNullOrUndefined(query)) {
                            this.data = query;
                        }
                        if (!this.isOutSiteUrl(this.data.url)) {
                            return this.data.url;
                        }
                        else {
                            return "http://cors-anywhere.herokuapp.com/" + this.data.url;
                        }
                    };
                    ImageEditorHelper.prototype.getFileNameFromUrl = function () {
                        var dfd = $.Deferred();
                        var urlElements = this.data.url.split("/"), fileName = urlElements[urlElements.length - 1];
                        if (this.data.isOutSiteUrl) {
                            dfd.resolve(fileName);
                        }
                        else {
                            nts.uk.request.ajax("/shr/infra/file/storage/infor/" + fileName).done(function (res) {
                                dfd.resolve(res.originalName);
                            }).fail(function (error) {
                                dfd.reject(error);
                            });
                        }
                        return dfd.promise();
                    };
                    ImageEditorHelper.prototype.isOutSiteUrl = function (url) {
                        return url.indexOf(nts.uk.request.location.siteRoot.rawUrl) < 0;
                    };
                    return ImageEditorHelper;
                }());
                ko.bindingHandlers['ntsImageEditor'] = new NtsImageEditorBindingHandler();
            })(koExtentions = ui_1.koExtentions || (ui_1.koExtentions = {}));
        })(ui = uk.ui || (uk.ui = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=imageeditor-ko.ext.js.map
/// <reference path="../../reference.ts"/>

module nts.uk.ui.koExtentions {

    /**
     * Dialog binding handler
     */
    class NtsImageEditorBindingHandler implements KnockoutBindingHandler {

        /**
         * Init. 
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext) {
            let data = valueAccessor();
            let editable = nts.uk.util.isNullOrUndefined(data.editable) ? false : ko.unwrap(data.editable);
            let zoomble = nts.uk.util.isNullOrUndefined(data.zoomble) ? false : ko.unwrap(data.zoomble);
            let width = nts.uk.util.isNullOrUndefined(data.width) ? 600 : ko.unwrap(data.width);
            let height = nts.uk.util.isNullOrUndefined(data.height) ? 600 : ko.unwrap(data.height);
            let croppable = false;
            
            let helper: ImageEditorHelper = new ImageEditorHelper();

            let $container = $("<div>", { 'class': 'image-editor-container' }),
                $element = $(element).append($container);

            let constructSite: ImageEditorConstructSite = new ImageEditorConstructSite($element, helper);


            let $uploadArea = $("<div>", { "class": "image-upload-container image-editor-area cf" });
            $container.append($uploadArea);

            if (editable === true) {
                croppable = true;
                let confirm = { checked: ko.observable(true) };
                $(element).data('checkbox', confirm);

                let $editContainer = $("<div>", { "class": "edit-action-container image-editor-area" });
                $container.append($editContainer);

                constructSite.buildCheckBoxArea(allBindingsAccessor, viewModel, bindingContext);
            }

            constructSite.buildActionArea();

            constructSite.buildUploadAction();

            constructSite.buildImagePreviewArea();

            constructSite.buildFileChangeHandler();

            constructSite.buildImageLoadedHandler(zoomble, croppable);

            constructSite.buildSrcChangeHandler();

            constructSite.buildImageDropEvent();
            
            $element.find(".image-holder").width(width).height(height);

            return { 'controlsDescendantBindings': true };
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            let data = valueAccessor(),
                $element = $(element),
                confirm = $(element).data('checkbox'),
                $checkbox = $element.find('.comfirm-checkbox');

            if (!nts.uk.util.isNullOrEmpty($checkbox)) {
                ko.bindingHandlers["ntsCheckBox"].update($checkbox[0], function() {
                    return confirm;
                }, allBindingsAccessor, viewModel, bindingContext);
            }
        }
    }

    class ImageEditorConstructSite {
        $root: JQuery;
        $previewArea: JQuery;
        $imagePreview: JQuery;
        $imageSizeLbl: JQuery;
        $imageNameLbl: JQuery;
        $inputFile: JQuery;
        $uploadBtn: JQuery;
        $checkbox: JQuery;

        helper: ImageEditorHelper;
        cropper: Cropper;

        constructor($root: JQuery, helper: ImageEditorHelper) {
            this.$root = $root;
            this.helper = helper;
        }

        buildCheckBoxArea(allBindingsAccessor, viewModel, bindingContext) {
            let self = this;
            let $checkboxHolder = $("<div>", { "class": "checkbox-holder image-editor-component" });
            let $editContainer = this.$root.find(".edit-action-container");
            $editContainer.append($checkboxHolder);

            this.$checkbox = $("<div>", { "class": "comfirm-checkbox style-button", text: "表示エリア選択する" });
            let $comment = $("<div>", { "class": "crop-description cf" });
            $checkboxHolder.append(this.$checkbox);
            $checkboxHolder.append($comment);

            let $cropAreaIcon = $("<div>", { "class": "crop-icon inline-container" });
            let $cropText = $("<div>", { "class": "crop-description-text inline-container" });
            let $mousePointerIcon = $("<div>", { "class": "mouse-icon inline-container" });
            let $mouseText = $("<div>", { "class": "mouse-description-text inline-container" });

            $("<label>", { "class": "info-label", "text": "のエリア内をメイン画面に表示します。" }).appendTo($cropText);
            $("<label>", { "class": "info-label", "text": "マウスのドラッグ＆ドロップでエリアを変更できます。" }).appendTo($mouseText);

            $comment.append($cropAreaIcon).append($cropText).append($mousePointerIcon).append($mouseText);

            let checkboxId = nts.uk.util.randomId();

            ko.bindingHandlers["ntsCheckBox"].init(this.$checkbox[0], function() {
                return self.$root.data('checkbox');
            }, allBindingsAccessor, viewModel, bindingContext);
        }

        buildActionArea() {
            this.$inputFile = $("<input>", { "class": "fileinput", "type": "file", "accept": this.helper.toStringExtension() })
                .appendTo($("<div>", { "class": "image-editor-component inline-container nts-fileupload-container" }));
            this.$imageNameLbl = $("<label>", { "class": "image-name-lbl info-label" })
                .appendTo($("<div>", { "class": "image-editor-component inline-container" }));
            this.$imageSizeLbl = $("<label>", { "class": "image-info-lbl info-label" })
                .appendTo(this.$imageNameLbl.parent());
            this.$uploadBtn = $("<button>", { "class": "upload-btn" })
                .appendTo($("<div>", { "class": "image-editor-component inline-container" }));

            let $uploadArea = this.$root.find(".image-upload-container");
            $uploadArea.append(this.$uploadBtn.parent());
            $uploadArea.append(this.$imageNameLbl.parent());
            $uploadArea.append(this.$inputFile.parent());
        }

        buildImagePreviewArea() {
            this.$previewArea = $("<div>", { "class": "image-preview-container image-editor-area" });
            this.$previewArea.appendTo(this.$root.find(".image-editor-container"));
            let imagePreviewId = nts.uk.util.randomId();

            let $imageHolder = $("<div>", { "class": "image-holder image-editor-component" }).appendTo(this.$previewArea);
            this.$imagePreview = $("<img>", { "class": "image-preview", "id": imagePreviewId }).appendTo($imageHolder);
        }

        buildUploadAction() {
            let self = this;
            self.$uploadBtn.text("参照").click(function(evt) {
                self.$inputFile.click();
            });
        }

        buildImageDropEvent() {
            let self = this;
            self.$previewArea.on('drop dragdrop', function(evt, ui) {
                event.preventDefault();
                let files = evt.originalEvent["dataTransfer"].files;
                if (!nts.uk.util.isNullOrEmpty(files)) {
                    let firstImageFile = self.helper.getFirstFile(files);
                    if (!nts.uk.util.isNullOrUndefined(firstImageFile)) {
                        self.assignImageToView(firstImageFile);
                    }
                }
            });

            this.$previewArea.on('dragenter', function(event) {
                event.preventDefault();
            });
            this.$previewArea.on('dragleave', function(evt, ui) {
            });
            this.$previewArea.on('dragover', function(event) {
                event.preventDefault();
            });
        }

        buildImageLoadedHandler(zoomble: boolean, croppable: boolean) {
            let self = this;
            self.$imagePreview.on('load', function() {
                var image = new Image();
                image.src = self.$imagePreview.attr("src");
                image.onload = function() {
                    self.$imageSizeLbl.text("　(大きさ " + this.height + "x" + this.width + "　　サイズ " + self.helper.getFileSize(self.$root.data("size")) + ")");

                    if (!nts.uk.util.isNullOrUndefined(self.cropper)) {
                        self.cropper.destroy();
                    }

                    self.cropper = new Cropper(self.$imagePreview[0], {
                        viewMode: 1,
                        dragMode: croppable ? "crop" : "none",
                        guides: false,
                        autoCrop: false,
                        highlight: false,
                        zoomable: zoomble,
                        crop: function(e) {
//                            console.log(e);
                        }, cropstart: function(e){
//                            e.preventDefault();
//                            console.log(e);
//                            return croppable;
                        }
                    });
                    self.$root.data("cropper", self.cropper);

                };
            });
        }

        buildSrcChangeHandler() {
            let self = this;
            self.$root.bind("srcchanging", function(evt, query?: SrcChangeQuery) {
                let target = self.helper.getUrl(query);
                var xhr = new XMLHttpRequest();
                xhr.open('GET', target);
                xhr.responseType = 'blob';

                xhr.onload = function(e) {
                    if (this.status == 200) {
                        var reader = new FileReader();
                        reader.readAsDataURL(xhr.response);
                        reader.onload = function() {
                            self.helper.getFileNameFromUrl().done(function(fileName) {
                                var fileType = xhr.response.type.split("/")[1],
                                    fileName = self.helper.data.isOutSiteUrl ? (fileName + "." + fileType) : fileName;
                                self.backupData(null, fileName, fileType, xhr.response.size);
                                self.$imagePreview.attr("src", reader.result);
                            });
                        };
                    }
                };
                xhr.send();
            });
        }

        buildFileChangeHandler() {
            let self = this;
            self.$inputFile.change(function() {
                if (nts.uk.util.isNullOrEmpty(this.files)) {
                    return;
                }

                self.assignImageToView(this.files[0]);
            });
        }

        assignImageToView(file) {
            let self = this;
            if (FileReader && file) {
                var fr = new FileReader();
                fr.onload = function() {
                    self.$imagePreview.attr("src", fr.result);
                    self.backupData(file, file.name, file.type.split("/")[1], file.size);
                }
                fr.readAsDataURL(file);
            }
        }

        private backupData(file, name, format, size) {
            let self = this;
            self.$root.data("file", file);
            self.$root.data("file-name", name);
            self.$root.data("file-type", format);
            self.$root.data("size", size);
            self.$imageNameLbl.text(name);
        }
    }

    class ImageEditorHelper {
        IMAGE_EXTENSION: Array<string> = [".png", ".jpg", ".jpeg"];
        data: SrcChangeQuery;
        BYTE_SIZE: number = 1024;
        SIZE_UNITS: Array<string> = ["BYTE", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"];

        constructor(query?: SrcChangeQuery, extensions?: Array<string>) {
            this.data = query;
            if (!nts.uk.util.isNullOrUndefined(extensions)) {
                this.IMAGE_EXTENSION = extensions;
            }
        }

        toStringExtension() {
            return this.IMAGE_EXTENSION.join(",");
        }

        getFirstFile(files: Array<File>) {
            let IMAGE_EXTENSION = this.IMAGE_EXTENSION;
            return _.find(files, function(file: File) {
                return _.find(IMAGE_EXTENSION, function(ie: string) {
                    return file.type.indexOf(ie.replace(".", "")) >= 0;
                }) !== undefined;
            });
        }

        getFileSize(originalSize: number) {
            let i = 0, result = originalSize;
            while (result > 5 * this.BYTE_SIZE) {
                result = result / this.BYTE_SIZE;
                i++;
            }
            let idx = i < this.SIZE_UNITS.length ? i : this.SIZE_UNITS.length - 1;
            return ntsNumber.trunc(result) + this.SIZE_UNITS[idx];
        }

        getUrl(query?: SrcChangeQuery) {
            if (!nts.uk.util.isNullOrUndefined(query)) {
                this.data = query;
            }

            if (!this.isOutSiteUrl(this.data.url)) {
                return this.data.url;
            } else {
                return `http://cors-anywhere.herokuapp.com/${this.data.url}`;
            }
        }

        getFileNameFromUrl() {
            let dfd = $.Deferred();
            let urlElements = this.data.url.split("/"),
                fileName = urlElements[urlElements.length - 1];
            if (this.data.isOutSiteUrl) {
                dfd.resolve(fileName);
            } else {
                nts.uk.request.ajax("/shr/infra/file/storage/infor/" + fileName).done(function(res) {
                    dfd.resolve(res.originalName);
                }).fail(function(error) {
                    dfd.reject(error);
                });
            }

            return dfd.promise();
        }

        private isOutSiteUrl(url: string): boolean {
            return url.indexOf(nts.uk.request.location.siteRoot.rawUrl) < 0;
        }
    }

    interface SrcChangeQuery {
        url: string,
        isOutSiteUrl: boolean
    }

    ko.bindingHandlers['ntsImageEditor'] = new NtsImageEditorBindingHandler();
}
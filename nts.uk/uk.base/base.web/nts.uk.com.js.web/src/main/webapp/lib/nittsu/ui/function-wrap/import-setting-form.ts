/// <reference path="../../reference.ts"/>

module nts.uk.ui {

    export module importSettingForm {
        /** The return result */
        export interface ImportSettingResult {
            encodeType: nts.uk.enums.EnumConstant,
            linebreakCode?: any,
            fileFormat?: any,
            includeHeader?: boolean
        }

        /** The option for ImportSettingForm */
        export interface ImportSetingOption {
            selector?: string,
            features?: ImportSettingFeature[],
            onClosed?: (result: ImportSettingResult) => void,
        }

        /** The ImportSettingForm feature */
        export interface ImportSettingFeature {
            name: FeatureName
        }
        type FeatureName = 'Encoding' | 'FileFormat' | 'LineBreak' | 'IncludeHeader';

        /** Main class ImportSettingForm */
        export class ImportSettingForm {
            private isInit: boolean;
            private option: ImportSetingOption;
            private result: ImportSettingResult;
            private $dialog: JQuery;
            private $encodeTypeCombo: JQuery;
            private EncodeTypes: nts.uk.enums.EnumConstant[];
            private defaultOption: ImportSetingOption = {
                selector: ".import-setting-trigger",
                features: [
                    { name: 'Encoding' }
                ],
                onClosed: $.noop()
            };

            /** Constructor
             *  Auto call Init()
             */
            constructor(option?: ImportSetingOption) {
                this.isInit = false;
                this.EncodeTypes = [
                    {
                        value: 0,
                        fieldName: "ShiftJIS",
                        localizedName: "Shift-JIS"
                    },
                    {
                        value: 1,
                        fieldName: "UTF8",
                        localizedName: "UTF-8"
                    },
                    {
                        value: 2,
                        fieldName: "UTF8BOM",
                        localizedName: "UTF-8 BOM"
                    },
                ];
                this.init(option);
            }

            /** Init ImportSettingForm */
            init(option?: ImportSetingOption) {
                var self = this;
                if (self.isInit === false) {
                    var rootScreen = nts.uk.ui.windows.container.windows["MAIN_WINDOW"];
                    var currentScreen = nts.uk.ui.windows.getSelf();
                    var $rootBody = $(rootScreen.globalContext.document).find("body");
                    self.isInit = true;
                    self.option = $.extend({}, this.defaultOption, option);
                    self.$dialog = $("<div id='" + nts.uk.util.randomId() + "'/>")
                        .css({
                            padding: '0px',
                            overflow: 'hidden'
                        })
                        .appendTo($rootBody)
                        .dialog({
                            autoOpen: false,
                            modal: true,
                            width: 500,
                            height: 400,
                            closeOnEscape: false,
                            open: function() {
                                self.result = undefined;
                            },
                            close: function(event) {
                                self.option.onClosed.call(this, self.result);
                            }
                        });

                    var $dialogTemplate = $("<div class='import-setting-container'/>").append("<div id='functions-area-bottom'/>").append("<div class='import-setting-body'/>");

                    // Build EncodeType
                    self.buildEncodeType($dialogTemplate);

                    // Function Area
                    var $proccedButton = $("<button class='x-large proceed'>決定</button>").on("click", (event) => {
                        self.result = {
                            encodeType: self.getEncodeType(),
                            linebreakCode: null,
                            fileFormat: null,
                            includeHeader: null
                        };
                        self.$dialog.dialog("close");
                    });
                    var $closeButton = $("<button class='large'>キャンセル</button>").on("click", (event) => { self.$dialog.dialog("close"); });
                    $dialogTemplate.find("#functions-area-bottom").append($proccedButton).append($closeButton);
                    $dialogTemplate.appendTo(self.$dialog);

                    // Binding Event to selector
                    $(self.option.selector).on("click.ImportSettingForm", self.show.bind(self))
                }
            }

            /** Destroy ImportSettingForm */
            destroy(): void {
                var self = this;
                if (self.isInit) {
                    self.isInit = false;
                    self.$dialog.dialog("destroy");
                    $(self.option.selector).off("click.ImportSettingForm");
                }
            }
            
            /** Recreate ImportSettingForm */
            refresh(option?: ImportSetingOption): void {
                this.destroy();
                this.init(option || this.option);
            }

            /** Show ImportSettingForm */
            show(): void {
                if (this.isInit)
                    this.$dialog.dialog("open");
            }

            /** Hide ImportSettingForm */
            hide(): void {
                if (this.isInit)
                    this.$dialog.dialog("close");
            }

            /** Build EncodeType Template */
            private buildEncodeType($dialogTemplate: JQuery): void {
                var self = this;
                self.$encodeTypeCombo = $("<div id='" + nts.uk.util.randomId() + "' class='encode-type'/>").appendTo($dialogTemplate.find(".import-setting-body"));
                self.$encodeTypeCombo.igCombo({
                    visibleItemsCount: 5,
                    dataSource: self.EncodeTypes,
                    valueKey: "value",
                    textKey: 'localizedName',
                    mode: "dropdown",
                    placeHolder: '',
                    tabIndex: -1,
                    enableClearButton: false
                });
                var dialogZindex = self.$dialog.closest(".ui-dialog").css("z-index");
                var $encodeTypeDropdown: JQuery = (<JQuery>self.$encodeTypeCombo.igCombo("dropDown")).css("z-index", Number(dialogZindex) + 10);
            }

            /** Get selected EncodeType */
            private getEncodeType(): nts.uk.enums.EnumConstant {
                var self = this;
                let encodeType = _.find(self.EncodeTypes, (item) => {
                    return item.value == self.$encodeTypeCombo.igCombo("value");
                });
                return encodeType;
            }
        }
    }
}
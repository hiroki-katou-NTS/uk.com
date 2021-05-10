/// <reference path="../reference.ts"/>

module nts.uk.ui {
    
    export module toBeResource {
        export let yes = "はい";
        export let no = "いいえ";
        export let cancel = "キャンセル";
        export let close = "閉じる";
        export let info = "情報";
        export let warn = "警告";
        export let error = "エラー";
        export let confirm = "確認";
        export let unset = "未設定";
        export let errorContent = "エラー内容";
        export let errorCode = "エラーコード";
        export let errorList = "エラー一覧";
        export let errorPoint = "エラー箇所";
        export let errorDetail = "エラー詳細";
        export let tab = "タブ";
        export let plzWait = "お待ちください";
        export let targetNotFound = "対象データがありません"; // FND_E_SEARCH_NOHITと統合したい
        export let clear = "解除";
        export let searchBox = "検索テキストボックス";
        export let addNewRow = "新規行の追加";
        export let deleteRow = "行の削除";
        export let selectMenu = "メニュー選択";
        export let manual = "マニュアル";
        export let logout = "ログアウト";
        export let settingPersonal = "個人情報の設定";
        export let weekDaysShort = [ "日", "月", "火", "水", "木", "金", "土" ];
        export let searchByCodeName = "コード・名称で検索・・・";
        export let search = "検索";
        export let filter = "絞り込み";
        export let code = "コード";
        export let codeAndName = "コード／名称";
        export let alphaNumeric = "半角英数字";
        export let katakana = "カタカナ";
        export let kana = "カナ";
        export let otherColors = "その他の色";
        export let hide = "隠す";
        export let decide = "確定";
        export let refer = "参照";
        export let selectViewArea = "表示エリアを選択する";
        export let showInsideAreaToMain = "のエリア内をメイン画面に表示します。";
        export let dragAndDropToChangeArea = "マウスのドラッグ＆ドロップでエリアを変更できます。";
        export let invalidImageData = "不正な画像データです。";
        export let legendExample = "凡例";
    }


    export function localize(textId: string): string {
        return textId;
    }
    
    export function writeViewConstraint(constraint: any){
        if(nts.uk.util.isNullOrUndefined(__viewContext.primitiveValueConstraints)){
            __viewContext.primitiveValueConstraints = {};
        }
        __viewContext.primitiveValueConstraints[constraint.itemCode] = constraint;
    }


    export var confirmSave: (dirtyChecker: DirtyChecker) => any;
    confirmSave = function(dirtyChecker: DirtyChecker) {
        var frame = windows.getSelf();
        if (frame.$dialog === undefined || frame.$dialog === null) {
            confirmSaveWindow(dirtyChecker);
        } else {
            confirmSaveDialog(dirtyChecker, frame.$dialog);
        }
    }
    function confirmSaveWindow(dirtyChecker: DirtyChecker) {

        var beforeunloadHandler = function(e) {
            if (dirtyChecker.isDirty()) {
                return "ban co muon save hok?";
                //return nts.ui.message('Com_0000105');
            }
        };

        confirmSaveEnable(beforeunloadHandler);
    }

    function confirmSaveDialog(dirtyChecker: DirtyChecker, dialog: JQuery) {
        //dialog* any;
        var beforeunloadHandler = function(e) {
            if (dirtyChecker.isDirty()) {
                e.preventDefault();
                nts.uk.ui.dialog.confirm("Are you sure you want to leave the page?")
                    .ifYes(function() {
                        dirtyChecker.reset();
                        dialog.dialog("close");
                    }).ifNo(function() {
                    })
                //return nts.ui.message('Com_0000105');
            }
        };

        confirmSaveEnableDialog(beforeunloadHandler, dialog);
    }

    export function confirmSaveEnableDialog(beforeunloadHandler, dialog) {
        dialog.on("dialogbeforeclose", beforeunloadHandler);
    };

    export function confirmSaveDisableDialog(dialog) {
        dialog.on("dialogbeforeclose", function() { });
    };

    export function confirmSaveEnable(beforeunloadHandler) {
        $(window).bind('beforeunload', beforeunloadHandler);
    };

    export function confirmSaveDisable() {
        $(window).unbind('beforeunload');
    };

    /**
     * Block UI Module
     * Using for blocking UI when action in progress
     */
    export module block {
        export function clear(el: HTMLElement = document.body) {
            const fadeOut = 200;

            $(el).unblock({ fadeOut });
        }

        export function grayout(el: HTMLElement = document.body) {
            const fadeIn = 200;
            const message: string = toBeResource.plzWait;
            const css = { width: '220px', 'line-height': '32px' };

            $(el).block({ message, fadeIn, css });
        }

        export function invisible(el: HTMLElement = document.body) {
            const message: null = null;
            const overlayCSS = { opacity: 0 };

            $(el).block({ message, overlayCSS });
        }
    }

    export class DirtyChecker {

        targetViewModel: KnockoutObservable<any>;
        initialState: string;

        constructor(targetViewModelObservable: KnockoutObservable<any>) {
            this.targetViewModel = targetViewModelObservable;
            this.initialState = this.getCurrentState();
        }

        getCurrentState() {
            return ko.toJSON(this.targetViewModel());
        }

        reset() {
            this.initialState = this.getCurrentState();
        }

        isDirty() {
            return this.initialState !== this.getCurrentState();
        }
    }


    module smallExtensions {

        $(() => {
            $(document).on('mouseenter', '.limited-label', e => {
                let $label = $(e.target);

                // Check if contents is overflow
                if (isOverflow($label)) {
                    let $view = $('<div />').addClass('limited-label-view')
                        .text($label.text() || $label.val())
                        .appendTo('body')
                        .position({
                            my: 'left top',
                            at: 'left bottom',
                            of: $label,
                            collision: 'flipfit'
                        });

                    if ($label.attr("disabled")) {
                        let id = "#" + $label.attr("id");
                        $(document).on('mouseleave.limitedlabel', id, () => {
                            $(document).off('mouseleave.limitedlabel', id);
                            $view.remove();
                        });
                        return;
                    }
                    
                    $label.bind('mouseleave.limitedlabel', () => {
                        $label.unbind('mouseleave.limitedlabel');
                        $view.remove();
                    });
                    
                    $label.on('remove', function() {
                        $view.remove();
                    });
                }
            });
        });
        
        function isOverflow($label) {
            if ( $label[0].nodeName === "INPUT"
                && (window.navigator.userAgent.indexOf("MSIE") > -1
                || !!window.navigator.userAgent.match(/trident/i))) {
                let $div = $("<div/>").appendTo($(document.body));
                let style = $label[0].currentStyle;
                if (style) {
                    for (let p in style) {
                        $div[0].style[p] = style[p];
                    }
                }
                
                $div.html($label.val());
                let width = $div.outerWidth();
                let scrollWidth = $div[0].scrollWidth;
                $div.remove();
                return width < scrollWidth;
            }
            
            return $label[0].offsetWidth < $label[0].scrollWidth;
        }
    }
    
    export module keyboardStream {
        
        let _lastKey: { code: number, time: Date } = {
            code: undefined,
            time: undefined
        };
        
        export function lastKey(): { code: number, time: Date } {
            return {
                code: _lastKey.code,
                time: _lastKey.time
            };
        }
        
        export function wasKeyDown(keyCode: number, millisToExpire: number): boolean {
            
            return _lastKey.code === keyCode
                && (+new Date() - +_lastKey.time <= millisToExpire);
        }
        
        $(() => {
            
            $(window).on("keydown", e => {
                _lastKey.code = e.keyCode;
                _lastKey.time = new Date();
            });
            
        });
    }

    module buttonExtension {
        // ボタンの上部分をクリックすると、ボタンの範囲からマウスカーソルが外れてしまい、
        // clickイベントが発生しなくなる不具合がある。
        // ダミーのdivを生成し、そこでmouseupイベントを拾うことで不具合を回避。

        // Emulate click event by cache & trigger
        $(() => {
            const cache: {
                button: Element | null;
                position: {
                    x: number;
                    y: number;
                } | null;
            } = {
                button: null,
                position: null
            };

            $(document)
                .on('mouseup', (evt: JQueryEventObject) => {
                    const { button, position } = cache;
                    const { target, pageX, pageY } = evt;

                    if (target.tagName !== 'BUTTON' && !$(target).closest('button').is(button)) {
                        if (button && position) {
                            const { x, y } = position;

                            if (x === pageX && y === pageY) {
                                // trigger click event of button if mouseup outside
                                $(button).trigger('click');
                            }
                        }
                    }

                    cache.button = null;
                    cache.position = null;
                })
                .on('mousedown', 'button', (evt: JQueryEventObject) => {
                    const { target } = evt;
                    cache.button = target.tagName === 'BUTTON' ? target : $(target).closest('button').get(0);
                    
                    cache.position = {
                        x: evt.pageX,
                        y: evt.pageY
                    };
                });
        });

        /*$(() => {
            $("body")
                .on("mousedown", "button", e => {
                    var $button = $(e.target);
                    var $dammy = $("<div>")
                        .css({
                            background: "transparent",
                            position: "absolute",
                            width: $button.outerWidth(),
                            height: parseInt($button.css("top"), 10),
                            cursor: "pointer",
                            opacity: 100
                        })
                        .appendTo("body")
                        .position({
                            my: "left bottom",
                            at: "left top",
                            of: e.target
                        })
                        .on("mouseup", eup => {
                            $dammy.remove();
                            $button.click();
                        });

                    $(window).on("mouseup.dammyevent", () => {
                        $dammy.remove();
                        $(window).off("mouseup.dammyevent");
                    });
                });
        });*/
    }
}

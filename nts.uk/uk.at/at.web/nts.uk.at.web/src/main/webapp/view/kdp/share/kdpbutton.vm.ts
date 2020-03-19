//module nts.uk.at.view.kdp.share {
//
//    export module viewmodel {
//
//        export class ScreenModel {
//
//            constructor() {
//
//            }
//
//            public startPage(): JQueryPromise<void> {
//                var dfd = $.Deferred<void>();
//                dfd.resolve();
//                return dfd.promise();
//
//            }
//
//            public init($input: JQuery, data: InfoCommon): JQueryPromise<void> {
//                var dfd = $.Deferred<void>();
//                let tdWidth: number = data.width / data.maxColumn;
//                let numberRow = data.infoButton.length / data.maxColumn;
//                let html: string = '<table> <tbody>';
//                for (i = 0; i < data.infoButton.length; i++) {
//                    if (i % data.maxColumn == 0) {
//                        html += '<tr>';
//
//                    }
//                    if (data.infoButton[i].buttonEmpty == true) {
//                        html += '<td style="width: ' + tdWidth + 'px ; height: ' + data.heighRow + 'px "; class="td-button">'
//                            + ' </button></td>'
//                    } else {
//                        html += '<td style="width: ' + tdWidth + 'px" class="td-button">'
//
//                            + '<button id = "'+ data.infoButton[i].idButton  + '" class = "button" style="height: ' + data.heighRow + 'px; width: ' + (tdWidth - 10) + 'px; background: ' + data.infoButton[i].background + ';">'
//                            //+ '<i class= ' + data.infoButton[i].iconClass + '></i> '
//                            + '<i class = "img-link" data-bind="ntsIcon: { no: 2, width: 30, height: 30 }"></i>'
//                            + ' <span style ="color: ' + data.infoButton[i].textColor + '; font-size: ' + data.infoButton[i].textSize + 'px" class="text-button"> ' + data.infoButton[i].contentText + ' </span>'
//                            + ' </button></td>'
//                    }
//                    if ((i % data.maxColumn == (data.maxColumn - 1)) || (i == data.infoButton.length - 1)) {
//                        html += '</tr>';
//                    }
//                }
//
//                html += '</tbody> </table>';
//                $input.append(html);
//                $("#" + $input[0].id+ " .button").click(function(value: any) {
//                    let process = data.clickProcess;
//                    process(value.currentTarget.innerText, value.currentTarget.id).done((valueResolve: any) => {
//                        console.log(valueResolve.disable);
//                        if(valueResolve.disable){
//                           $('#' + value.currentTarget.id).attr('disabled', true);
//                        }
//                    })
//                });
//                dfd.resolve();
//                return dfd.promise();
//
//            }
//
//        interface InfoButton {
//            idButton: string;
//            background: string;
//            textColor: string;
//            iconClass: string;
//            textSize: number;
//            contentText: string;
//            buttonEmpty: boolean;
//        }
//
//        interface InfoCommon {
//            width: number;
//            heighRow: number;
//            maxColumn: number;
//            infoButton: Array<InfoButton>;
//            clickProcess: any;
//        }
//
//    }
//}
//interface JQuery {
//    ntsGridButton(option: nts.uk.at.view.kdp.share.viewmodel.InfoCommon): JQueryPromise<void>;
//}
//
//(function($: any) {
//    $.fn.ntsGridButton = function(option: nts.uk.at.view.kdp.share.viewmodel.InfoCommon): JQueryPromise<void> {
//
//        // Return.
//        return new nts.uk.at.view.kdp.share.viewmodel.ScreenModel().init(this, option);
//    }
//
//} (jQuery));
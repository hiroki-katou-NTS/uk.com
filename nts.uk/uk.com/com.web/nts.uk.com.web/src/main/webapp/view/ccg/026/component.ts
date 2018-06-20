module nts.uk.com.view.ccg026.component {
    import ajax = nts.uk.request.ajax;
    import text = nts.uk.resource.getText;

    const fetch = {
        com_mngr: (roleId: string) => ajax('com', 'sys/auth/workplace/get-list', roleId),
        person_info: (roleId: string) => ajax('com', 'ctx/pereg/functions/auth/find-all', roleId)
    };

    ko.components.register('ccg026-component', {
        template: `
        <!-- ko let: { text: nts.uk.resource.getText } -->
        <!-- ko if: ko.toJS(label.text) -->
        <div data-bind="ntsFormLabel: {
                required: label.require,
                text: text(ko.toJS(label.text))
            },
            style: {
                'margin-bottom': '10px'
            }"></div>
        <!-- /ko -->
        <style type="text/css">
            #permision_grid td,
            #permision_grid_headers th {
                border-top: 0;
                border-right: 0;
                line-height: 25px;
            }

            #permision_grid td:first-child,
            #permision_grid_headers th:first-child {
                border-left: 0;
            }
    
            #permision_grid td .ntsCheckBox {
                display: block;
                text-align: center;
            }

            #permision_grid tr:last-child td {
                border-bottom: 0;
            }

            #container_permision_grid {
                border: 1px solid #ccc;
            }

            #permision_grid .ntsCheckBox-label {
                outline: none;
                display: inline;
            }

            #permision_grid .ntsCheckBox-label:focus span.box {
                border-color: #0096f2;
                box-shadow: 0 0 1px 1px #0096f2;
            }
        </style>
        <div id="container_permision_grid">
            <table id='permision_grid'></table>
        </div>
        <!-- /ko -->
    `,
        viewModel: {
            createViewModel: (params: IParam, componentInfo: IComponentInfo) => {
                let $element = $(componentInfo.element),
                    $grid = $element.find('#permision_grid'),
                    $container = $element.find('#container_permision_grid'),
                    requestDone = (data: Array<IPermision>) => {
                        data = _.orderBy(data, ['orderNumber']);

                        $grid = $element.find('#permision_grid');

                        $grid.igGrid("option", "dataSource", data);
                    };

                _.extend(params, {
                    label: {
                        text: ((params || { label: { text: '' } }).label || { text: '' }).text,
                        require: ((params || { label: { require: false } }).label || { require: false }).require
                    }
                });

                // extend roleId & roleType
                if (params.roleId && params.roleType) {
                    if (!ko.isObservable(params.roleId)) {
                        _.extend(params, {
                            roleId: ko.observable(params.roleId)
                        });
                    }

                    if (!ko.isObservable(params.roleType)) {
                        _.extend(params, {
                            roleType: ko.observable(params.roleType)
                        });
                    }
                } else {
                    _.extend(params, {
                        roleId: ko.observable(''),
                        roleType: ko.observable('')
                    });
                }

                params.roleId.subscribe(v => {
                    params.roleType.valueHasMutated();
                });

                // fetch data when subscribe
                params.roleType.subscribe(v => {
                    let roleId = ko.toJS(params.roleId),
                        oldData = $element.data('OLD_DATA') || { roleId: undefined, roleType: undefined },
                        compare = {
                            roleId: roleId,
                            roleType: v
                        };

                    if (!_.isEqual(oldData, compare)) {
                        switch (v) {
                            case ROLE_TYPE.COMPANY_MANAGER:
                                fetch.com_mngr(roleId).done(requestDone);
                                break;
                            case ROLE_TYPE.PERSONAL_INFO:
                                fetch.person_info(roleId).done(requestDone);
                                break;
                            default:
                                break;
                        }

                        $element.data('OLD_DATA', compare);
                    }
                });
                params.roleType.valueHasMutated();

                $grid
                    .igGrid({
                        width: "100%",
                        height: "217px",
                        primaryKey: "functionNo",
                        enableHoverStyles: false,
                        columns: [
                            { headerText: 'コード', key: 'functionNo', hidden: true },
                            { headerText: text('CCG026_2'), key: 'functionName', width: "200px" },
                            {
                                headerText: text('CCG026_3'), key: 'available', width: "80px",
                                template: '<div class="ntsControl ntsCheckBox">\
                                            <label class="ntsCheckBox-label">\
                                                <input type="checkbox" value="${functionNo}">\
                                                <span class="box"></span>\
                                            </label>\
                                       </div>'
                            },
                            { headerText: text('CCG026_4'), key: 'description', width: "200px" }
                        ],
                        dataRendered: (evt, ui) => {
                            // remove all tabindex of control
                            $container.find('[tabindex=0]').removeAttr('tabindex');

                            // set tabindex for checkbox
                            $container.find('label.ntsCheckBox-label')
                                .attr('tabindex', params.tabindex || 0)
                                .on('keypress', (evt) => {
                                    if ([13, 32].indexOf(evt.keyCode) > -1) {
                                        $(evt.target).find('input[type="checkbox"]').trigger('click');
                                        return false;
                                    }
                                });

                            // check and bind change event to checkbox
                            $container.find('input[type="checkbox"]')
                                .each((i, input: HTMLInputElement) => {
                                    $grid = $element.find('#permision_grid');

                                    let data = $grid.igGrid("option", "dataSource"),
                                        row = _.find(data, r => _.isEqual(Number(r['functionNo']), Number(input.value)));

                                    if (row && row['available']) {
                                        input.checked = true;
                                    }

                                    $(input)
                                        .on('change', (evt) => {
                                            // change
                                            row['available'] = !!input.checked;

                                            // push data to viewModel
                                            params.changeData(data);
                                        });
                                });

                            //send first data
                            $container.find('input[type="checkbox"]:first').trigger('change');
                        },
                        dataSource: []
                    });

                return params;
            }
        }
    });

    export interface IPermision {
        available: boolean;
        description: string;
        functionName: string;
        functionNo: number;
        orderNumber: number;
    }

    interface IComponentInfo {
        element: HTMLElement,
        templateNodes: Array<Node>
    }

    export interface IParam {
        label?: ILabel;
        tabindex?: number | string;
        roleId: KnockoutObservable<string>;
        roleType: KnockoutObservable<ROLE_TYPE>;
        changeData?: (data?: any) => void;
    }

    export interface ILabel {
        text: string;
        require?: boolean;
    }

    export enum ROLE_TYPE {
        /** The system manager. */
        // システム管理者
        SYSTEM_MANAGER = 0,
        /** The company manager. */
        // 会社管理者
        COMPANY_MANAGER = 1,
        /** The group comapny manager. */
        // グループ会社管理者
        GROUP_COMAPNY_MANAGER = 2,
        /** The employment. */
        // 就業
        EMPLOYMENT = 3,
        /** The salary. */
        // 給与
        SALARY = 4,
        /** The human resource. */
        // 人事
        HUMAN_RESOURCE = 5,
        /** The office helper. */
        // オフィスヘルパー
        OFFICE_HELPER = 6,
        /** The my number. */
        // マイナンバー
        MY_NUMBER = 7,
        /** The personal info. */
        // 個人情報
        PERSONAL_INFO = 8
    }
}
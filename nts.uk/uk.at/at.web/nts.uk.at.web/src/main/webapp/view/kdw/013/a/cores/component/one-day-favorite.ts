module nts.uk.ui.at.kdw013.onedayfavorite {

    @handler({
        bindingName: 'kdw013-oneday-events'
    })
    export class Kdw013OneDayEventBindingHandler implements KnockoutBindingHandler {
        init = (element: HTMLElement, componentName: () => string, allBindingsAccessor: KnockoutAllBindingsAccessor, __: any, bindingContext: KnockoutBindingContext): { controlsDescendantBindings: boolean; } => {
            const name = componentName();
            const mode = allBindingsAccessor.get('mode');
            const items = allBindingsAccessor.get('items');
            const setting = allBindingsAccessor.get('$settings');
            const oneDayFavoriteSet = allBindingsAccessor.get('oneDayFavoriteSet');            
            const params = { mode, items, oneDayFavoriteSet };

            ko.applyBindingsToNode(element, { component: { name, params } });

            return { controlsDescendantBindings: true };
            
        }
    }



    @component({
        name: 'kdw013-oneday-events',
        template: `
            <div class='edit-popup'>
                    <ul>
                        <li class='popupButton-g' data-bind="i18n: 'KDW013_77' ,click:$component.openGdialog"></li>
                        <li data-bind="i18n: 'KDW013_78' ,click:$component.removeFav"></li>
                    </ul>
            </div>
            <div data-bind="ntsAccordion: { active: 0}">
                <h3>
                    <label data-bind="i18n: 'KDW013_76'"></label>
                </h3>
                <div class='fc-events fc-oneday-events'>
                    <ul data-bind="foreach: { data: $component.params.items, as: 'item' }">
                        <li class="title" data-bind="attr: {
                            'data-id': _.get(item.extendedProps, 'relateId', ''),
                            'data-color': item.backgroundColor
                        }">
                            <div data-bind="style: {
                                'background-color': item.backgroundColor
                            }"></div>
                            <div style="display: flex;">
                                <label  class='limited-label' style='width:90%;cursor: pointer;'  data-bind='text: item.title'>
                                </label>
                                <i data-bind="ntsIcon: { no: 2, width: 20, height: 25  },click: function(item,evn) { $component.editFav(evn,_.get(item.extendedProps, 'favId', '')) }">
                                </i>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            <style rel="stylesheet">
                .fc-container .fc-oneday-events .edit-popup{
                    visibility: hidden;
                    position: fixed;
                    z-index: 99;
                    box-shadow: 1px 1px 5px 2px #888;
                    background-color: #fff;
                    padding: 0 10px;
                }
                .fc-container .fc-oneday-events .edit-popup ul li{
                    cursor: pointer;
                    padding: 5px 5px;
                }
                .fc-container .fc-oneday-events .edit-popup ul li:hover{
                    background: #CDE2CD;
                    color: #0086EA ;
                }
                .fc-container .fc-oneday-events .show {
                    visibility: visible;
                }
            </style>
           `
    })
    export class Kdw013OneDayEventComponent extends ko.ViewModel {
        constructor(public params: EventParams) {
            super();
        }

        editFav(e, id) {
            let editPopup = $('.fc-oneday-events .edit-popup');
            let pst = e.target;
            if (!pst) {
                editPopup.removeClass('show');
            }
            const { top, left, height, width } = pst.getBoundingClientRect();
            editPopup.addClass('show');
            editPopup.css({ "top": top, "left": left + width });
            editPopup.data('favId', id);
        }

        removeFav(data) {
            const vm = this;
            let id = $('.fc-oneday-events .edit-popup').data('favId');

            let newArrays = _.filter(vm.params.items(), item => {
                return _.get(item, 'extendedProps.favId') != id;
            });

            vm.params.items(newArrays);
            $('.fc-oneday-events .edit-popup').removeClass('show');
        }

        openGdialog(data) {
            const vm = this;
            $('.fc-oneday-events .edit-popup').removeClass('show');
            let id = $('.fc-oneday-events .edit-popup').data('favId');
            let item = _.find(vm.params.items(), item => _.get(item, 'extendedProps.favId') == id);
            //gọi màn G
            vm.params.oneDayFavoriteSet({
                // 社員ID
                sId: vm.$user.employeeId,
                // お気に入りID
                favId: id,
                // お気に入り作業名称
                taskName: item.title,
                // お気に入り内容
                taskBlockDetailContents: item.extendedProps.dropInfo.taskBlockDetailContents
            });
        }
    }

    type EventParams = {
        items: KnockoutObservableArray<any>;
        mode: KnockoutComputed<boolean>;
        oneDayFavoriteSet: any;
    }; 

}
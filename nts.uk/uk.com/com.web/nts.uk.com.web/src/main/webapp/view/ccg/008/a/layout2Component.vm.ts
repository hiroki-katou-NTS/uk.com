/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.com.view.ccg008.a.Layout2ComponentViewModel {

  @component({
    name: 'layout2-component',
    template: 
    `
      <div class="panel panel-frame widget_contents" data-bind="foreach: $component.lstWidgetLayout">
        <span data-bind="if: url.indexOf('.xhtml') > -1">
          <iframe style="width:450px" data-bind="attr: { src: url }" />
        </span>
        <span data-bind="if: url.indexOf('.js') > -1">
          <div data-bind="component: { name: name }"></div>
        </span>
      </div>
    `
  })
  export class Layout2ComponentViewModel extends ko.ViewModel {
    lstWidgetLayout: KnockoutObservableArray<ItemLayout> = ko.observableArray([]);
    created(param: any) {
      const vm = this;
      const layout2 = param.item();
      let dataLayout: ItemLayout[] = [];
        vm.lstWidgetLayout([]);
        if (layout2) {
          _.each(layout2, (item: WidgetSettingDto) => {
            const itemLayout: ItemLayout = new ItemLayout();
            itemLayout.url = origin + vm.getUrlAndName(item.widgetType).url;
            itemLayout.name = vm.getUrlAndName(item.widgetType).name
            itemLayout.order = item.order;
            dataLayout.push(itemLayout);
          });
          dataLayout = _.orderBy(dataLayout, ["order"], ["asc"]);
          vm.lstWidgetLayout(dataLayout);
        }
    }

    getUrlAndName(type: any) {
      let url = "";
      let name = ''
      switch (type) {
        case 0:
          url = "/nts.uk.at.web/view/ktg/005/a/index.xhtml";
          name = '';
          break;
        case 1:
          url = "/nts.uk.at.web/view/ktg/001/a/index.xhtml";
          name = '';
          break;
        case 2:
          url = "/nts.uk.at.web/view/ktg/004/a/index.xhtml";
          name = '';
          break;
        case 3:
          url = "/view/ktg/027/a/ktg026component.a.vm.js";
          name = 'ktg026-component';
          break;
        case 4:
          url = "/view/ktg/027/a/ktg027.a.vm.js";
          name = 'ktg027-component';
          break;
        case 5:
          url = "/nts.uk.at.web/view/kdp/001/a/index.xhtml";
          name = '';
          break;
        case 6:
          url = "/nts.uk.at.web/view/ktg/031/a/index.xhtml";
          name = '';
          break;
        case 7:
          url = "/view/ccg/005/a/index.xhtml";
          name = '';
          break;
      }
      return {url: url, name: name};
    }
  }

  export class ItemLayout {
    url: string;
    html: string;
    name: string;
    order: number;
    constructor(init?: Partial<ItemLayout>) {
      $.extend(this, init);
    }
  }

  export class WidgetSettingDto {
    widgetType: number;
    order: number;
    constructor(init?: Partial<WidgetSettingDto>) {
      $.extend(this, init);
    }
  }
  
}
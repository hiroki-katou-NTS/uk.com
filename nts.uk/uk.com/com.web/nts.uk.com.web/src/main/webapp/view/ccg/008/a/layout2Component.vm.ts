/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
import character = nts.uk.characteristics;
module nts.uk.com.view.ccg008.a.Layout2ComponentViewModel {
  @component({
    name: "layout2-component",
    template: `
      <span data-bind="if: $component.isLayout == 2">
        <div class="widget_contents" data-bind="foreach: $component.lstWidgetLayout">
          <span data-bind="if: url.indexOf('.xhtml') > -1">
            <iframe style="width:450px" data-bind="attr: { src: url }" />
          </span>
          <span data-bind="if: url.indexOf('.js') > -1">
            <div data-bind="attr:{ id: 'WG2-' + $index() }">
              <div data-bind="component: { name: name }" style="margin-bottom: 10px; height:100%;" ></div>
            </div>
          </span>
        </div>
      </span>
      <span data-bind="if: $component.isLayout == 3">
        <div class="widget_contents" data-bind="foreach: $component.lstWidgetLayout">
          <span data-bind="if: url.indexOf('.xhtml') > -1">
            <iframe style="width:450px" data-bind="attr: { src: url }" />
          </span>
          <span data-bind="if: url.indexOf('.js') > -1">
            <div data-bind="attr:{ id: 'WG3-' + $index() }">
              <div data-bind="component: { name: name }" style="margin-bottom: 10px; height:100%;" ></div>
            </div>
          </span>
        </div>
      </span>
    `,
  })
  export class Layout2ComponentViewModel extends ko.ViewModel {
    lstWidgetLayout: KnockoutObservableArray<ItemLayout> = ko.observableArray([]);
    isLayout: number;

    created(param: any) {
      const vm = this;
      const layout2 = param.item();
      const origin: string = window.location.origin;
      vm.isLayout = param.isLayout;
      let dataLayout: ItemLayout[] = [];
      vm.lstWidgetLayout([]);
      if (layout2) {
        _.each(layout2, (item: WidgetSettingDto) => {
          const itemLayout: ItemLayout = new ItemLayout();
          itemLayout.url = origin + vm.getUrlAndName(item.widgetType).url;
          itemLayout.name = vm.getUrlAndName(item.widgetType).name;
          itemLayout.order = item.order;
          dataLayout.push(itemLayout);
        });
        dataLayout = _.orderBy(dataLayout, ["order"], ["asc"]);
        vm.lstWidgetLayout(dataLayout);
      }
      character.restore("widgetSize").then((data: WidgetSizeWithUser) => {
        if(data) {
          if (data.sId === __viewContext.user.employeeId) {
            for (let i = 0; i < 6; i++) {
              if ($(`#WG2-${i}`)) {
                  const wg0 = $(`#WG2-${i}`)[0];
                  if(wg0) {
                    const wg0Child = wg0.firstElementChild.firstElementChild as any;
                  let dataWg = data.widget.filter(e => e.widgetName === wg0Child.id);
                  if(dataWg.length > 0) {
                    wg0Child.style.height = dataWg[0].height + "px";
                  }
                }
              }
              if ($(`#WG3-${i}`)) {
                const wg1 = $(`#WG3-${i}`)[0];
                if(wg1) {
                  const wg1Child = wg1.firstElementChild.firstElementChild as any;
                let dataWg = data.widget.filter(e => e.widgetName === wg1Child.id);
                if(dataWg.length > 0) {
                  wg1Child.style.height = dataWg[0].height + "px";
                }
              }
            }
            }
          }
        }
      });
      
    }

    getUrlAndName(type: any) {
      let url = "";
      let name = "";
      switch (type) {
        case 0:
          url = "/nts.uk.at.web/view/ktg/005/a/index.xhtml";
          name = "";
          break;
        case 1:
          url = "/nts.uk.at.web/view/ktg/001/a/index.xhtml";
          name = "";
          break;
        case 2:
          url = "/nts.uk.at.web/view/ktg/004/a/index.xhtml";
          name = "";
          break;
        case 3:
          url = "/view/ktg/026/a/ktg026component.a.vm.js";
          name = "ktg026-component";
          break;
        case 4:
          url = "/view/ktg/027/a/ktg027.a.vm.js";
          name = "ktg027-component";
          break;
        case 5:
          url = "/nts.uk.at.web/view/kdp/001/a/index.xhtml";
          name = "";
          break;
        case 6:
          url = "/view/ktg/031/a/ktg031.a.vm.js";
          name = "ktg031-component";
          break;
        case 7:
          url = "/nts.uk.com.web//view/ccg/005/a/ccg005.a.vm.js";
          name = 'ccg005-component';
          break;
      }
      return { url: url, name: name };
    }

    mounted() {
      const vm = this;
      let resizeTimer = 0;
      for (let i = 0; i < 6; i++) {
        if ($(`#WG2-${i}`)) {
          $(`#WG2-${i}`).resizable({
            grid: [10000, 1]
          });
          $(`#WG2-${i}`).resize(() => {
            const wg0 = $(`#WG2-${i}`)[0];
            const wg0Child = wg0.firstElementChild.firstElementChild as any;
            character.restore("widgetSize").then((data: WidgetSizeWithUser) => {
              if(data) {
                if (data.sId === __viewContext.user.employeeId) {
                    let dataWg: WidgetSize[] = data.widget.filter(e => e.widgetName === wg0Child.id);
                  if(dataWg.length > 0) {
                    let dataUpdate = data.widget.filter(e => e.widgetName !== wg0Child.id);
                    dataWg[0].height = wg0.scrollHeight;
                    dataUpdate.push(dataWg[0]);
                    data.widget  = dataUpdate;
                    character.save("widgetSize", data);
                  } else {
                    let widget = new WidgetSize();
                    widget.widgetName = wg0Child.id;
                    widget.height = wg0.scrollHeight;
                    data.widget.push(widget);
                    character.save("widgetSize", data);
                  }
                } else {
                  let widgetWithUser = new WidgetSizeWithUser();
                  let widget = new WidgetSize();
                  widgetWithUser.sId = __viewContext.user.employeeId;
                  widget.widgetName = wg0Child.id;
                  widget.height = wg0.scrollHeight;
                  widgetWithUser.widget.push(widget);
                  character.save("widgetSize", widgetWithUser);
                }
              } else {
                let widgetWithUser = new WidgetSizeWithUser();
                let widget = new WidgetSize();
                widgetWithUser.sId = __viewContext.user.employeeId;
                widget.widgetName = wg0Child.id;
                widget.height = wg0.scrollHeight;
                widgetWithUser.widget.push(widget);
                character.save("widgetSize", widgetWithUser);
              } 
            });
            wg0Child.style.height = '100%';
            if (_.indexOf(wg0Child, 'ccg005') >= 0) {
              clearTimeout(resizeTimer);
              resizeTimer = setTimeout(() => $(window).trigger('ccg005.resize'), 100);
            }
          })
        }
      }

      for (let i = 0; i < 6; i++) {
        if ($(`#WG3-${i}`)) {
          $(`#WG3-${i}`).resizable({
            grid: [10000, 1]
          });
          $(`#WG3-${i}`).resize(() => {
            const wg0 = $(`#WG3-${i}`)[0];
            const wg0Child = wg0.firstElementChild.firstElementChild as any;
            character.restore("widgetSize").then((data: WidgetSizeWithUser) => {
              if(data) {
                if (data.sId === __viewContext.user.employeeId) {
                    let dataWg: WidgetSize[] = data.widget.filter(e => e.widgetName === wg0Child.id);
                  if(dataWg.length > 0) {
                    let dataUpdate = data.widget.filter(e => e.widgetName !== wg0Child.id);
                    dataWg[0].height = wg0.scrollHeight;
                    dataUpdate.push(dataWg[0]);
                    data.widget  = dataUpdate;
                    character.save("widgetSize", data);
                  } else {
                    let widget = new WidgetSize();
                    widget.widgetName = wg0Child.id;
                    widget.height = wg0.scrollHeight;
                    data.widget.push(widget);
                    character.save("widgetSize", data);
                  }
                } else {
                  let widgetWithUser = new WidgetSizeWithUser();
                  let widget = new WidgetSize();
                  widgetWithUser.sId = __viewContext.user.employeeId;
                  widget.widgetName = wg0Child.id;
                  widget.height = wg0.scrollHeight;
                  widgetWithUser.widget.push(widget);
                  character.save("widgetSize", widgetWithUser);
                }
              } else {
                let widgetWithUser = new WidgetSizeWithUser();
                let widget = new WidgetSize();
                widgetWithUser.sId = __viewContext.user.employeeId;
                widget.widgetName = wg0Child.id;
                widget.height = wg0.scrollHeight;
                widgetWithUser.widget.push(widget);
                character.save("widgetSize", widgetWithUser);
              } 
            });
            wg0Child.style.height = '100%';
            if (wg0Child.getAttribute('id').indexOf('ccg005') >= 0) {
              clearTimeout(resizeTimer);
              resizeTimer = setTimeout(() => $(window).trigger('ccg005.resize'), 100);
            }
          })
        }
      }
      vm.$el.querySelectorAll("iframe").forEach((frame) => {
        const targetNode = frame.contentDocument;

        if (targetNode) {
          const config = { childList: true, subtree: true };

          const callback = function () {
            const width = frame.contentWindow.innerWidth;
            const height = frame.contentWindow.innerHeight;
            frame.style.width = width.toString();
            frame.style.height = height.toString();
          };
          const observer = new MutationObserver(callback);
          observer.observe(targetNode, config);
        }
      });
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
  
  export class WidgetSize {
    widgetName: string;
    height: number;
    constructor(init?: Partial<WidgetSettingDto>) {
      $.extend(this, init);
    }
  }

  export class WidgetSizeWithUser {
    sId: string;
    widget: WidgetSize[] = [];
    constructor(init?: Partial<WidgetSettingDto>) {
      $.extend(this, init);
    }
  }
}
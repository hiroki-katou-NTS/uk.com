/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.com.view.ccg034.d {

  // URL API backend
  const API = {
    generateHtml: "sys/portal/createflowmenu/generateHtml",
    updateLayout: "sys/portal/createflowmenu/updateLayout",
  }

  const KEY_DATA_PART_TYPE: string = 'data-part-type';
  const MENU_CREATION_LAYOUT_ID: string = 'menu-creation-layout';
  const ITEM_HIGHLIGHT_ID: string = 'item-highlight';
  const ITEM_COPY_PLACEHOLDER_ID: string = 'item-copy-placeholder';
  const KEY_DATA_ITEM_CLIENT_ID: string = 'data-item-client-id';
  const CELL_SIZE: number = 40;
  const CREATION_LAYOUT_WIDTH: number = 1920;
  const CREATION_LAYOUT_HEIGHT: number = 1080;

  @bean()
  export class ScreenModel extends ko.ViewModel {
    $menuCreationLayoutContainer: JQuery = null;
    $menuCreationLayout: JQuery = null;
    $hoverHighlight: JQuery = null;
    $copyPlaceholder: JQuery = null;
    $listPart: JQuery[] = [];
    partClientId: number = 0;
    mapPartData: any = {};
    layoutSizeText: KnockoutObservable<string> = ko.observable('');
    flowMenuCode: KnockoutObservable<string> = ko.observable(null);
    flowMenuFileId: KnockoutObservable<string> = ko.observable(null);
    flowMenuData: KnockoutObservable<FlowMenuLayoutDto> = ko.observable(null);
    menuName: KnockoutObservable<string> = ko.observable(null);

    isMouseInsideLayout: KnockoutObservable<boolean> = ko.observable(false);
    isCopying: KnockoutObservable<boolean> = ko.observable(false);
    copyingPartId: KnockoutObservable<number> = ko.observable(null);
    layoutOffsetLeft: KnockoutObservable<number> = ko.observable(null);
    layoutOffsetTop: KnockoutObservable<number> = ko.observable(null);

    created(params: any) {
      const vm = this;
      vm.flowMenuCode(params.flowMenuCode);
      if (params.flowMenuData) {
        const flowMenuData: FlowMenuLayoutDto = params.flowMenuData;
        vm.flowMenuFileId(flowMenuData.fileId);
        vm.flowMenuData(flowMenuData);
        vm.menuName(`${vm.flowMenuCode()} ${vm.flowMenuData().flowMenuName}`);
      }
    }

    mounted() {
      const vm = this;
      // Init text resource
      vm.layoutSizeText(vm.$i18n('CCG034_50', [CREATION_LAYOUT_WIDTH.toString(), CREATION_LAYOUT_HEIGHT.toString()]));
      // Store creation layout as class variable for easier access
      vm.$menuCreationLayout = $(`#${MENU_CREATION_LAYOUT_ID}`);
      vm.$menuCreationLayoutContainer = $('.menu-creation-layout-container');
      vm.$menuCreationLayout
        .outerWidth(CREATION_LAYOUT_WIDTH)
        .outerHeight(CREATION_LAYOUT_HEIGHT);
      // Init dragable item
      $(".menu-creation-option").draggable({
        appendTo: `#${MENU_CREATION_LAYOUT_ID}`,
        helper: "clone",
        start: (event, ui) => {
          LayoutUtils.startDragItemFromMenu(ui);
        },
        drag: (event, ui) => {
          const partSize = LayoutUtils.getPartSize(ui.helper.attr(KEY_DATA_PART_TYPE));
          vm.renderHoveringItemOnDrag(ui, partSize.width, partSize.height);
        },
        stop: (event, ui) => {
          vm.$hoverHighlight.remove();
          if (vm.isMouseInsideLayout()) {
            vm.createItemFromMenu(ui, ui.helper.attr(KEY_DATA_PART_TYPE));
          }
        },
      });
      // Init dropable layout
      vm.$menuCreationLayout
        .droppable({
          accept: ".menu-creation-item-container",
        })
        .mouseenter(() => vm.isMouseInsideLayout(true))
        .mouseleave(() => vm.isMouseInsideLayout(false))
        .mousedown((event) => {
          // If layout in copy mode and mouse cursor is stay inside layout
          if (vm.isCopying() && vm.isMouseInsideLayout()) {
            // Stop copy mode
            vm.isCopying(false);
            // Clear mouse event handler
            vm.$menuCreationLayout.off('mousemove');
            // Remove placeholder
            vm.$copyPlaceholder.remove();
            vm.$hoverHighlight.remove();
            // Create copy item
            const offsetX = event.pageX - vm.layoutOffsetLeft() + vm.$menuCreationLayoutContainer.scrollLeft();
            const offsetY = event.pageY - vm.layoutOffsetTop() + vm.$menuCreationLayoutContainer.scrollTop();
            // Calculate copy item div position
            const oldPartData = vm.mapPartData[vm.copyingPartId()];
            const positionTop: number = LayoutUtils.calculatePositionTop(oldPartData.height, offsetY);
            const positionLeft: number = LayoutUtils.calculatePositionLeft(oldPartData.width, offsetX);
            // Check overlap
            const overlappingParts: JQuery[] = vm.getOverlappingPart(new PartDataModel({
              width: oldPartData.width,
              height: oldPartData.height,
              positionTop: positionTop,
              positionLeft: positionLeft,
            }));
            if (!overlappingParts.length) {
              // Create new part div
              const newPartData: PartDataModel = vm.copyPartData(oldPartData, positionTop, positionLeft);
              vm.createDOMFromData(newPartData);
            }
          }
        });
      // Load part DOMs to creation layout
      if (vm.flowMenuData()) {
        vm.$blockui('grayout');
        vm.loadPartDomToLayout(vm.flowMenuData());
        vm.$blockui('clear');
      }
    }

    /**
     * Load part DOMs to creation layout
     */
    private loadPartDomToLayout(flowData: any): void {
      const vm = this;
      const $partDOMs: JQuery[] = [];
      // MenuSettingDto
      for (const partDataDto of flowData.menuData) {
        const newPartData = vm.createPartDataFromDtoMenu(partDataDto);
        // Set part data to layout
        $partDOMs.push(vm.createDOMFromData(newPartData));
      }
      // LabelSettingDto
      for (const partDataDto of flowData.labelData) {
        const newPartData = vm.createPartDataFromDtoLabel(partDataDto);
        // Set part data to layout
        $partDOMs.push(vm.createDOMFromData(newPartData));
      }
      // LinkSettingDto
      for (const partDataDto of flowData.linkData) {
        const newPartData = vm.createPartDataFromDtoLink(partDataDto);
        // Set part data to layout
        $partDOMs.push(vm.createDOMFromData(newPartData));
      }
      // FileAttachmentSettingDto
      for (const partDataDto of flowData.fileAttachmentData) {
        const newPartData = vm.createPartDataFromDtoFileAttachment(partDataDto);
        // Set part data to layout
        $partDOMs.push(vm.createDOMFromData(newPartData));
      }
      // ImageSettingDto
      for (const partDataDto of flowData.imageData) {
        const newPartData = vm.createPartDataFromDtoImage(partDataDto);
        // let img = new Image();
        // img.src = newPartData.isFixed === 0 ? newPartData.fileName : (nts.uk.request as any).liveView(newPartData.fileId);
        // console.log(img);
        // console.log(img.naturalHeight);
        // console.log(img.naturalWidth);
        // newPartData.ratio = img.naturalHeight / img.naturalWidth;
        // console.log(newPartData);
        // Set part data to layout
        $partDOMs.push(vm.createDOMFromData(newPartData));
      }
      // ArrowSettingDto
      for (const partDataDto of flowData.arrowData) {
        const newPartData = vm.createPartDataFromDtoArrow(partDataDto);
        // Set part data to layout
        $partDOMs.push(vm.createDOMFromData(newPartData));
      }
      // Append new part to layout
      vm.$menuCreationLayout.append($partDOMs);
    }

    /**
     * Create new item on drop from menu
     * @param item
     */
    private createItemFromMenu(part: JQueryUI.DraggableEventUIParams, partType: string) {
      const vm = this;
      const partSize = LayoutUtils.getPartSize(partType);
      // Calculate new part div position
      const positionTop: number = part.position.top > 0 ? Math.round(part.position.top / CELL_SIZE) * CELL_SIZE : 0;
      const positionLeft: number = part.position.left > 0 ? Math.round(part.position.left / CELL_SIZE) * CELL_SIZE : 0;
      // Check overlap
      const overlappingParts: JQuery[] = vm.getOverlappingPart(new PartDataModel({
        width: partSize.width,
        height: partSize.height,
        positionTop: positionTop,
        positionLeft: positionLeft,
      }));
      if (!overlappingParts.length) {
        // Create new part div
        const newPartData: PartDataModel = vm.createDefaultPartData(partType, partSize, positionTop, positionLeft);
        // Check if overlap is allowed or not
        const $newPart: JQuery = vm.createDOMFromData(newPartData);
        // Open PartSetting Dialog
        vm.openPartSettingDialog($newPart, true);
      }
    }

    /**
     * Create new DOM based on part data
     */
    private createDOMFromData(partData: PartDataModel): JQuery {
      const vm = this;
      let $newPartTemplate = null;
      switch (partData.partType) {
        case MenuPartType.PART_MENU:
          $newPartTemplate = $("<div>", { "class": 'menu-creation-item-container' }).append($('<div>', { 'class': 'menu-creation-item part-menu' }));
          break;
        case MenuPartType.PART_LABEL:
          $newPartTemplate = $("<div>", { "class": 'menu-creation-item-container' }).append($('<div>', { 'class': 'menu-creation-item part-label' }));
          break;
        case MenuPartType.PART_LINK:
          $newPartTemplate = $("<div>", { "class": 'menu-creation-item-container' }).append($('<div>', { 'class': 'menu-creation-item part-link' }));
          break;
        case MenuPartType.PART_ATTACHMENT:
          $newPartTemplate = $("<div>", { "class": 'menu-creation-item-container' }).append($('<div>', { 'class': 'menu-creation-item part-attachment' }));
          break;
        case MenuPartType.PART_IMAGE:
          $newPartTemplate = $("<div>", { "class": 'menu-creation-item-container' }).append($('<div>', { 'class': 'menu-creation-item part-image' }));
          break;
        case MenuPartType.PART_ARROW:
          $newPartTemplate = $("<div>", { "class": 'menu-creation-item-container' }).append($('<div>', { 'class': 'menu-creation-item part-arrow' }));
          break;
        default:
          $newPartTemplate = $("<div>", { "class": 'menu-creation-item-container' }).append($('<div>', { 'class': 'menu-creation-item part-menu' }));
          break;
      }
      const $newPart: JQuery = LayoutUtils.renderPartDOM($newPartTemplate, partData);
      // Render div setting
      const $partSetting: JQuery = $("<div>", { "class": 'part-setting' })
        .hover(
          (handlerIn) => LayoutUtils.onPartClickSetting($newPart, true),
          (handlerOut) => LayoutUtils.onPartClickSetting($newPart, false));
      const $partSettingPopup: JQuery = $("<div>", { "class": 'part-setting-popup' })
        .css({ 'display': 'none' })
        .append($("<div>", { "class": 'part-setting-popup-option', text: vm.$i18n('CCG034_150') })
          .on('click', (event) => {
            LayoutUtils.onPartClickSetting($newPart, false);
            vm.openPartSettingDialog($newPart);
          }))
        .append($("<div>", { "class": 'part-setting-popup-option', text: vm.$i18n('CCG034_151') })
          .on('click', (event) => {
            LayoutUtils.onPartClickSetting($newPart, false);
            vm.copyPart($newPart);
          }))
        .append($("<div>", { "class": 'part-setting-popup-option', text: vm.$i18n('CCG034_152') })
          .on('click', (event) => {
            LayoutUtils.onPartClickSetting($newPart, false);
            vm.removePart($newPart);
          }));
      $partSettingPopup.appendTo($partSetting);
      $partSetting.appendTo($newPart);
      // Check and remove overlap part (both DOM element and data by calling JQuery.remove())
      // vm.filterOverlappingPart(partData); // No need for filter overlap part
      // Add new item to origin list
      vm.$listPart.push($newPart);
      // Append to creation layout
      vm.$menuCreationLayout.append($newPart);
      // Init selectable creation layout
      vm.$menuCreationLayout.selectable({
        selected: (event, ui) => {
          $(ui.selected)
            .addClass("ui-selected")
            .siblings()
            .removeClass("ui-selected");
          // Wait for UI refresh
          vm.$nextTick(() => {
            if ($(ui.selected).hasClass('menu-creation-item-container')) {
              // Only allow dragable + resize on selected menu item
              if ($(ui.selected).hasClass('ui-selected')) {
                $(ui.selected)
                  // Init/enable resize
                  .resizable({
                    disabled: false,
                    resize: (eventResizing, uiResizing) => vm.onItemResizing(eventResizing, uiResizing),
                    stop: (eventResizeStop, uiResizeStop) => vm.onItemStopResize(eventResizeStop, uiResizeStop),
                  })
                  // Init/enable dragable
                  .draggable({
                    disabled: false,
                    containment: `#${MENU_CREATION_LAYOUT_ID}`,
                    drag: (eventDraging, uiDraging) => vm.onItemDraging(eventDraging, uiDraging),
                    stop: (eventDragStop, uiDragStop) => vm.onItemDragStop(eventDragStop, uiDragStop),
                  });
              } else {
                // Disable dragable + resize on unselected menu item
                $(ui.selected)
                  .resizable({ disabled: true })
                  .draggable({ disabled: true });
              }
            }
          });
        },
        unselected: (event, ui) => {
          // Disable dragable + resize on unselected menu item
          if ($(ui.unselected).hasClass('menu-creation-item-container')) {
            $(ui.unselected)
              .resizable({ disabled: true })
              .draggable({ disabled: true });
            $(ui.unselected)
              .find('.part-setting-popup')
              .css({ 'display': 'none' });
          }
        }
      });
      return $newPart;
    }

    private onItemResizing(event: Event, ui: JQueryUI.ResizableUIParams) {
      const vm = this;
      const partClientId: number = Number(ui.element.attr(KEY_DATA_ITEM_CLIENT_ID));
      const partData: PartDataModel = vm.mapPartData[partClientId];
      vm.renderHoveringItemOnResize(ui, partData);
    }

    private onItemStopResize(event: Event, ui: JQueryUI.ResizableUIParams) {
      const vm = this;
      vm.$hoverHighlight.remove();
      vm.resizeItem(ui);
    }

    private onItemDraging(event: Event, ui: JQueryUI.DraggableEventUIParams) {
      const vm = this;
      const partDataClientId: number = Number(ui.helper.attr(KEY_DATA_ITEM_CLIENT_ID));
      const partData: PartDataModel = vm.mapPartData[partDataClientId];
      vm.renderHoveringItemOnDrag(ui, partData.width, partData.height);
    }

    private onItemDragStop(event: Event, ui: JQueryUI.DraggableEventUIParams) {
      const vm = this;
      vm.$hoverHighlight.remove();
      if (vm.isMouseInsideLayout()) {
        vm.moveItem(ui);
      } else {
        vm.cancelMoveItem(ui);
      }
    }

    /**
     * Render hovering highlight effect on drag
     * @param item
     */
    private renderHoveringItemOnDrag(item: JQueryUI.DraggableEventUIParams, width: number, height: number) {
      const vm = this;
      // Parent layout must have position: relative for item.position to be corrected
      const partClientId: number = Number(item.helper.attr(KEY_DATA_ITEM_CLIENT_ID));
      // Calculate highlight div position
      const positionTop: number = LayoutUtils.calculatePositionTop(height, item.position.top);
      const positionLeft: number = LayoutUtils.calculatePositionLeft(width, item.position.left);
      vm.renderHoveringItem(item.helper, partClientId, width, height, positionTop, positionLeft);
    }

    /**
     * Render hovering highlight effect on resize
     * @param item
     * @param minWidth
     * @param minHeight
     */
    private renderHoveringItemOnResize(item: JQueryUI.ResizableUIParams, partData: PartDataModel) {
      const vm = this;
      // Calculate highlight div size
      const width: number = item.element.width() > partData.minWidth ? Math.ceil(item.element.width() / CELL_SIZE) * CELL_SIZE : partData.minWidth;
      const height: number = item.element.height() > partData.minHeight ? Math.ceil(item.element.height() / CELL_SIZE) * CELL_SIZE : partData.minHeight;
      vm.renderHoveringItem(item.element, partData.clientId, width, height, partData.positionTop, partData.positionLeft);
    }

    /**
     * Render hovering highlight effect
     * @param width
     * @param height
     * @param positionTop
     * @param positionLeft
     */
    private renderHoveringItem($originPart: JQuery, partClientId: number, width: number, height: number, positionTop: number, positionLeft: number) {
      const vm = this;
      // If not existed, create new highlight div
      if (!vm.$hoverHighlight) {
        vm.$hoverHighlight = $("<div>", { id: ITEM_HIGHLIGHT_ID, "class": "menu-creation-item-highlight" });
      }
      // Set more attr (highlight width, height, position)
      if (vm.isMouseInsideLayout()) {
        vm.$hoverHighlight
          .outerWidth(width)
          .outerHeight(height)
          .css({
            'visibility': 'visible',
            'top': `${positionTop}px`,
            'left': `${positionLeft}px`
          });
      } else {
        vm.$hoverHighlight.css({
          'visibility': 'hidden',
        });
      }
      // Append to creation layout
      vm.$menuCreationLayout.append(vm.$hoverHighlight);
      // Check overlap
      const overlappingParts: JQuery[] = vm.getOverlappingPart(new PartDataModel({
        clientId: partClientId,
        width: width,
        height: height,
        positionTop: positionTop,
        positionLeft: positionLeft,
      }));
      if (overlappingParts.length) {
        // Overlapped, change cursor to not allow
        $originPart.css({ 'cursor': 'not-allowed' });
        $originPart
          .children('.ui-resizable-e')
          .css({ 'cursor': 'not-allowed' });
        $originPart
          .children('.ui-resizable-s')
          .css({ 'cursor': 'not-allowed' });
        $originPart
          .children('.ui-resizable-se')
          .css({ 'cursor': 'not-allowed' });
      } else {
        // Not overlapped, change cursor to normal
        $originPart.css({ 'cursor': 'auto' });
        $originPart
          .children('.ui-resizable-e')
          .css({ 'cursor': 'e-resize' });
        $originPart
          .children('.ui-resizable-s')
          .css({ 'cursor': 's-resize' });
        $originPart
          .children('.ui-resizable-se')
          .css({ 'cursor': 'se-resize' });
      }
    }

    /**
     * Resize item on stop resizing
     * @param item
     */
    private resizeItem(item: JQueryUI.ResizableUIParams) {
      const vm = this;
      const partClientId: number = Number(item.element.attr(KEY_DATA_ITEM_CLIENT_ID));
      const partData: PartDataModel = vm.mapPartData[partClientId];
      // Calculate highlight div size
      const width: number = item.element.width() > partData.minWidth ? Math.ceil(item.element.width() / CELL_SIZE) * CELL_SIZE : partData.minWidth;
      const height: number = item.element.height() > partData.minHeight ? Math.ceil(item.element.height() / CELL_SIZE) * CELL_SIZE : partData.minHeight;
      // Update width + height
      const resizedPartData = $.extend({}, partData);
      resizedPartData.width = width;
      resizedPartData.height = height;
      // Check overlap
      const overlappingParts: JQuery[] = vm.getOverlappingPart(resizedPartData);
      if (overlappingParts.length) {
        // Revert
        item.element.css({ 'cursor': 'auto' });
        item.element
          .children('.ui-resizable-e')
          .css({ 'cursor': 'e-resize' });
        item.element
          .children('.ui-resizable-s')
          .css({ 'cursor': 's-resize' });
        item.element
          .children('.ui-resizable-se')
          .css({ 'cursor': 'se-resize' });
        vm.cancelResizeItem(item);
      } else {
        // Update part data to map, Update part DOM, Check and remove overlap part (both DOM element and data by calling JQuery.remove())
        vm.mapPartData[partClientId] = resizedPartData;
        LayoutUtils.renderPartDOM(item.element, resizedPartData);
        // vm.filterOverlappingPart(resizedPartData); // No need for filter overlap part
      }
    }

    /**
     * Move item on stop dragging
     * @param item
     */
    private moveItem(item: JQueryUI.DraggableEventUIParams) {
      const vm = this;
      const partClientId: number = Number(item.helper.attr(KEY_DATA_ITEM_CLIENT_ID));
      const partData: PartDataModel = vm.mapPartData[partClientId];
      // Calculate highlight div position
      const positionTop: number = LayoutUtils.calculatePositionTop(partData.height, item.position.top);
      const positionLeft: number = LayoutUtils.calculatePositionLeft(partData.width, item.position.left);
      // Update positionTop + positionLeft
      const movedPartData = $.extend({}, partData);
      movedPartData.positionTop = positionTop;
      movedPartData.positionLeft = positionLeft;
      // Check overlap
      const overlappingParts: JQuery[] = vm.getOverlappingPart(movedPartData);
      if (overlappingParts.length) {
        // Revert
        item.helper.css({ 'cursor': 'auto' });
        item.helper
          .children('.ui-resizable-e')
          .css({ 'cursor': 'e-resize' });
        item.helper
          .children('.ui-resizable-s')
          .css({ 'cursor': 's-resize' });
        item.helper
          .children('.ui-resizable-se')
          .css({ 'cursor': 'se-resize' });
        vm.cancelMoveItem(item);
      } else {
        // Update part data to map, Update part DOM, Check and remove overlap part (both DOM element and data by calling JQuery.remove())
        vm.mapPartData[partClientId] = movedPartData;
        LayoutUtils.renderPartDOM(item.helper, movedPartData);
        // vm.filterOverlappingPart(resizedPartData); // No need for filter overlap part
      }
    }

    /**
     * Cancel resize item
     */
    private cancelResizeItem(item: JQueryUI.ResizableUIParams) {
      const vm = this;
      const partClientId: number = Number(item.element.attr(KEY_DATA_ITEM_CLIENT_ID));
      const partData: PartDataModel = vm.mapPartData[partClientId];
      // Update part DOM
      LayoutUtils.renderPartDOM(item.element, partData);
    }

    /**
     * Cancel move item
     */
    private cancelMoveItem(item: JQueryUI.DraggableEventUIParams) {
      const vm = this;
      const partClientId: number = Number(item.helper.attr(KEY_DATA_ITEM_CLIENT_ID));
      const partData: PartDataModel = vm.mapPartData[partClientId];
      // Update part DOM
      LayoutUtils.renderPartDOM(item.helper, partData);
    }

    /**
     * Check and remove overlapping part from creation layout
     * @param checkingPart
     */
    private filterOverlappingPart(checkingPart: PartDataModel) {
      const vm = this;
      // Check and remove overlap part (both DOM element and data by calling JQuery.remove())
      const overlappingParts: JQuery[] = vm.getOverlappingPart(checkingPart);
      _.forEach(overlappingParts, (part) => part.remove());
      // Filter overlap part reference from origin list
      vm.$listPart = _.filter(vm.$listPart, ($part) => {
        const partClientId: number = Number($part.attr(KEY_DATA_ITEM_CLIENT_ID));
        return !LayoutUtils.isItemOverlapping(checkingPart, vm.mapPartData[partClientId]);
      });
    }

    /**
     * Get overlapping Part
     */
    private getOverlappingPart(checkingPart: PartDataModel): JQuery[] {
      const vm = this;
      // Check and remove overlap part (both DOM element and data by calling JQuery.remove())
      return _.filter(vm.$listPart, ($part) => {
        const partClientId: number = Number($part.attr(KEY_DATA_ITEM_CLIENT_ID));
        return LayoutUtils.isItemOverlapping(checkingPart, vm.mapPartData[partClientId]);
      });
    }

    /**
     * Create default part data
     * @param partType
     * @param partSize
     * @param positionTop
     * @param positionLeft
     */
    private createDefaultPartData(partType: string, partSize: PartSize, positionTop: number, positionLeft: number): PartDataModel {
      const vm = this;
      let newPartData: PartDataModel = null;
      switch (partType) {
        case MenuPartType.PART_MENU:
          newPartData = new PartDataMenuModel({
            // PartData
            clientId: vm.partClientId,
            width: partSize.width,
            height: partSize.height,
            minWidth: CELL_SIZE,
            minHeight: CELL_SIZE,
            partType: partType,
            positionTop: positionTop,
            positionLeft: positionLeft,
            // PartDataMenuModel
          });
          break;
        case MenuPartType.PART_LABEL:
          newPartData = new PartDataLabelModel({
            // PartData
            clientId: vm.partClientId,
            width: partSize.width,
            height: partSize.height,
            minWidth: CELL_SIZE,
            minHeight: CELL_SIZE,
            partType: partType,
            positionTop: positionTop,
            positionLeft: positionLeft,
            // PartDataLabelModel
          });
          break;
        case MenuPartType.PART_LINK:
          newPartData = new PartDataLinkModel({
            // PartData
            clientId: vm.partClientId,
            width: partSize.width,
            height: partSize.height,
            minWidth: CELL_SIZE,
            minHeight: CELL_SIZE,
            partType: partType,
            positionTop: positionTop,
            positionLeft: positionLeft,
            // PartDataLinkModel
          });
          break;
        case MenuPartType.PART_ATTACHMENT:
          newPartData = new PartDataAttachmentModel({
            // PartData
            clientId: vm.partClientId,
            width: partSize.width,
            height: partSize.height,
            minWidth: CELL_SIZE,
            minHeight: CELL_SIZE,
            partType: partType,
            positionTop: positionTop,
            positionLeft: positionLeft,
            // PartDataAttachmentModel
          });
          break;
        case MenuPartType.PART_IMAGE:
          newPartData = new PartDataImageModel({
            // PartData
            clientId: vm.partClientId,
            width: partSize.width,
            height: partSize.height,
            minWidth: CELL_SIZE,
            minHeight: CELL_SIZE,
            partType: partType,
            positionTop: positionTop,
            positionLeft: positionLeft,
            // PartDataImageModel
          });
          break;
        case MenuPartType.PART_ARROW:
          newPartData = new PartDataArrowModel({
            // PartData
            clientId: vm.partClientId,
            width: partSize.width,
            height: partSize.height,
            minWidth: CELL_SIZE,
            minHeight: CELL_SIZE,
            partType: partType,
            positionTop: positionTop,
            positionLeft: positionLeft,
            // PartDataArrowModel
          });
          break;
        default:
          newPartData = new PartDataMenuModel();
          break;
      }
      // Set part data to map
      vm.mapPartData[vm.partClientId] = newPartData;
      vm.partClientId++;
      return newPartData;
    }

    /**
     * Create part data from MenuSettingDto
     */
    private createPartDataFromDtoMenu(dto: MenuSettingDto): PartDataMenuModel {
      const vm = this;
      const newPartData: PartDataMenuModel = new PartDataMenuModel({
        // Common data
        clientId: vm.partClientId,
        width: dto.width,
        height: dto.height,
        minWidth: CELL_SIZE,
        minHeight: CELL_SIZE,
        partType: MenuPartType.PART_MENU,
        positionTop: dto.row * CELL_SIZE,
        positionLeft: dto.column * CELL_SIZE,
        // Menu data
        alignHorizontal: dto.horizontalPosition,
        alignVertical: dto.verticalPosition,
        menuCode: dto.menuCode,
        menuName: dto.menuName,
        menuClassification: dto.menuClassification,
        systemType: dto.systemType,
        fontSize: dto.fontSize,
        isBold: dto.bold === 1,
        menuUrl: null,  // TODO
      });
      // Set part data to map
      vm.mapPartData[vm.partClientId] = newPartData;
      vm.partClientId++;
      return newPartData;
    }

    /**
     * Create part data from LabelSettingDto
     */
    private createPartDataFromDtoLabel(dto: LabelSettingDto): PartDataLabelModel {
      const vm = this;
      const newPartData: PartDataLabelModel = new PartDataLabelModel({
        // Common data
        clientId: vm.partClientId,
        width: dto.width,
        height: dto.height,
        minWidth: CELL_SIZE,
        minHeight: CELL_SIZE,
        partType: MenuPartType.PART_LABEL,
        positionTop: dto.row * CELL_SIZE,
        positionLeft: dto.column * CELL_SIZE,
        // Label data
        alignHorizontal: dto.horizontalPosition,
        alignVertical: dto.verticalPosition,
        labelContent: dto.labelContent,
        fontSize: dto.fontSize,
        isBold: dto.bold === 1,
        textColor: dto.textColor,
        backgroundColor: dto.backgroundColor,
      });
      // Set part data to map
      vm.mapPartData[vm.partClientId] = newPartData;
      vm.partClientId++;
      return newPartData;
    }

    /**
     * Create part data from LinkSettingDto
     */
    private createPartDataFromDtoLink(dto: LinkSettingDto): PartDataLinkModel {
      const vm = this;
      const newPartData: PartDataLinkModel = new PartDataLinkModel({
        // Common data
        clientId: vm.partClientId,
        width: dto.width,
        height: dto.height,
        minWidth: CELL_SIZE,
        minHeight: CELL_SIZE,
        partType: MenuPartType.PART_LINK,
        positionTop: dto.row * CELL_SIZE,
        positionLeft: dto.column * CELL_SIZE,
        // Link data
        alignHorizontal: dto.horizontalPosition,
        alignVertical: dto.verticalPosition,
        url: dto.url,
        linkContent: dto.linkContent,
        fontSize: dto.fontSize,
        isBold: dto.bold === 1,
      });
      // Set part data to map
      vm.mapPartData[vm.partClientId] = newPartData;
      vm.partClientId++;
      return newPartData;
    }

    /**
     * Create part data from FileAttachmentSettingDto
     */
    private createPartDataFromDtoFileAttachment(dto: FileAttachmentSettingDto): PartDataAttachmentModel {
      const vm = this;
      const newPartData: PartDataAttachmentModel = new PartDataAttachmentModel({
        // Common data
        clientId: vm.partClientId,
        width: dto.width,
        height: dto.height,
        minWidth: CELL_SIZE,
        minHeight: CELL_SIZE,
        partType: MenuPartType.PART_ATTACHMENT,
        positionTop: dto.row * CELL_SIZE,
        positionLeft: dto.column * CELL_SIZE,
        // Attachment data
        alignHorizontal: dto.horizontalPosition,
        alignVertical: dto.verticalPosition,
        fileId: dto.fileId,
        fileSize: null,
        fileName: null,
        fileLink: null, // TODO
        linkContent: dto.linkContent,
        fontSize: dto.fontSize,
        isBold: dto.bold === 1,
      });
      // Set part data to map
      vm.mapPartData[vm.partClientId] = newPartData;
      vm.partClientId++;
      return newPartData;
    }

    /**
     * Create part data from ImageSettingDto
     */
    private createPartDataFromDtoImage(dto: ImageSettingDto): PartDataImageModel {
      const vm = this;
      const newPartData: PartDataImageModel = new PartDataImageModel({
        // Common data
        clientId: vm.partClientId,
        width: dto.width,
        height: dto.height,
        minWidth: CELL_SIZE,
        minHeight: CELL_SIZE,
        partType: MenuPartType.PART_IMAGE,
        positionTop: dto.row * CELL_SIZE,
        positionLeft: dto.column * CELL_SIZE,
        // Image data
        fileId: dto.fileId,
        fileName: dto.fileName,
        uploadedFileName: null,
        uploadedFileSize: null,
        isFixed: dto.isFixed,
        ratio: 1, // TODO
      });
      // Set part data to map
      vm.mapPartData[vm.partClientId] = newPartData;
      vm.partClientId++;
      return newPartData;
    }

    /**
     * Create part data from ArrowSettingDto
     */
    private createPartDataFromDtoArrow(dto: ArrowSettingDto): PartDataArrowModel {
      const vm = this;
      const newPartData: PartDataArrowModel = new PartDataArrowModel({
        // Common data
        clientId: vm.partClientId,
        width: dto.width,
        height: dto.height,
        minWidth: CELL_SIZE,
        minHeight: CELL_SIZE,
        partType: MenuPartType.PART_ARROW,
        positionTop: dto.row * CELL_SIZE,
        positionLeft: dto.column * CELL_SIZE,
        // Arrow data
        fileName: dto.fileName,
        fileSrc: dto.fileName,
      });
      // Set part data to map
      vm.mapPartData[vm.partClientId] = newPartData;
      vm.partClientId++;
      return newPartData;
    }

    /**
     * Copy part data
     */
    private copyPartData(originPartData: PartDataModel, positionTop: number, positionLeft: number): PartDataModel {
      const vm = this;
      let newPartData: PartDataModel = null;
      switch (originPartData.partType) {
        case MenuPartType.PART_MENU:
          newPartData = new PartDataMenuModel(originPartData);
          break;
        case MenuPartType.PART_LABEL:
          newPartData = new PartDataLabelModel(originPartData);
          break;
        case MenuPartType.PART_LINK:
          newPartData = new PartDataLinkModel(originPartData);
          break;
        case MenuPartType.PART_ATTACHMENT:
          newPartData = new PartDataAttachmentModel(originPartData);
          break;
        case MenuPartType.PART_IMAGE:
          newPartData = new PartDataImageModel(originPartData);
          break;
        case MenuPartType.PART_ARROW:
          newPartData = new PartDataArrowModel(originPartData);
          break;
        default:
          newPartData = new PartDataMenuModel(originPartData);
          break;
      }
      // Set part data to map
      newPartData.clientId = vm.partClientId;
      newPartData.positionTop = positionTop;
      newPartData.positionLeft = positionLeft;
      vm.mapPartData[vm.partClientId] = newPartData;
      vm.partClientId++;
      return newPartData;
    }

    /**
     * Open Part Setting Dialog
     * @param partClientId
     */
    private openPartSettingDialog($part: JQuery, isCreateDialog?: boolean) {
      const vm = this;
      const partClientId: number = Number($part.attr(KEY_DATA_ITEM_CLIENT_ID));
      const selectedPartData: PartDataModel = vm.mapPartData[partClientId];
      if (selectedPartData) {
        switch (selectedPartData.partType) {
          case MenuPartType.PART_MENU:
            vm.$window.modal('/view/ccg/034/f/index.xhtml', selectedPartData)
              .then((result: PartDataModel) => {
                if (result) {
                  // Update part data
                  vm.mapPartData[partClientId] = result;
                  // Update part DOM
                  LayoutUtils.renderPartDOMMenu($part, result as PartDataMenuModel);
                } else {
                  if (isCreateDialog) {
                    // If this is dialog setitng when create => remove part
                    vm.removePart($part);
                  }
                }
              });
            break;
          case MenuPartType.PART_LABEL:
            vm.$window.modal('/view/ccg/034/e/index.xhtml', selectedPartData)
              .then((result: PartDataModel) => {
                if (result) {
                  // Update part data
                  vm.mapPartData[partClientId] = result;
                  // Update part DOM
                  LayoutUtils.renderPartDOMLabel($part, result as PartDataLabelModel);
                } else {
                  if (isCreateDialog) {
                    // If this is dialog setitng when create => remove part
                    vm.removePart($part);
                  }
                }
              });
            break;
          case MenuPartType.PART_LINK:
            vm.$window.modal('/view/ccg/034/g/index.xhtml', selectedPartData)
              .then((result: PartDataModel) => {
                if (result) {
                  // Update part data
                  vm.mapPartData[partClientId] = result;
                  // Update part DOM
                  LayoutUtils.renderPartDOMLink($part, result as PartDataLinkModel);
                } else {
                  if (isCreateDialog) {
                    // If this is dialog setitng when create => remove part
                    vm.removePart($part);
                  }
                }
              });
            break;
          case MenuPartType.PART_ATTACHMENT:
            vm.$window.modal('/view/ccg/034/h/index.xhtml', selectedPartData)
              .then((result: PartDataModel) => {
                if (result) {
                  // Update part data
                  vm.mapPartData[partClientId] = result;
                  // Update part DOM
                  LayoutUtils.renderPartDOMAttachment($part, result as PartDataAttachmentModel);
                } else {
                  if (isCreateDialog) {
                    // If this is dialog setitng when create => remove part
                    vm.removePart($part);
                  }
                }
              });
            break;
          case MenuPartType.PART_IMAGE:
            vm.$window.modal('/view/ccg/034/i/index.xhtml', selectedPartData)
              .then((result: PartDataModel) => {
                if (result) {
                  // Update part data
                  vm.mapPartData[partClientId] = result;
                  // Update part DOM
                  LayoutUtils.renderPartDOMImage($part, result as PartDataImageModel);
                } else {
                  if (isCreateDialog) {
                    // If this is dialog setitng when create => remove part
                    vm.removePart($part);
                  }
                }
              });
            break;
          case MenuPartType.PART_ARROW:
            vm.$window.modal('/view/ccg/034/j/index.xhtml', selectedPartData)
              .then((result: PartDataModel) => {
                if (result) {
                  // Update part data
                  vm.mapPartData[partClientId] = result;
                  // Update part DOM
                  LayoutUtils.renderPartDOMArrow($part, result as PartDataArrowModel);
                } else {
                  if (isCreateDialog) {
                    // If this is dialog setitng when create => remove part
                    vm.removePart($part);
                  }
                }
              });
            break;
          default:
            break;
        }
      }
    }

    /**
     * Copy part
     * @param $part
     */
    private copyPart($part: JQuery) {
      const vm = this;
      const partClientId: number = Number($part.attr(KEY_DATA_ITEM_CLIENT_ID));
      const partData = vm.mapPartData[partClientId];
      // Start copy mode
      vm.isCopying(true);
      vm.copyingPartId(partClientId);
      // Create new placeholder div
      switch (partData.partType) {
        case MenuPartType.PART_MENU:
          vm.$copyPlaceholder = $("<div>", { id: ITEM_COPY_PLACEHOLDER_ID, "class": 'menu-creation-item-copy-placeholder' })
            .append($('<div>', { 'class': 'menu-creation-item part-menu' }));
          break;
        case MenuPartType.PART_LABEL:
          vm.$copyPlaceholder = $("<div>", { id: ITEM_COPY_PLACEHOLDER_ID, "class": 'menu-creation-item-copy-placeholder' })
            .append($('<div>', { 'class': 'menu-creation-item part-label' }));
          break;
        case MenuPartType.PART_LINK:
          vm.$copyPlaceholder = $("<div>", { id: ITEM_COPY_PLACEHOLDER_ID, "class": 'menu-creation-item-copy-placeholder' })
            .append($('<div>', { 'class': 'menu-creation-item part-link' }));
          break;
        case MenuPartType.PART_ATTACHMENT:
          vm.$copyPlaceholder = $("<div>", { id: ITEM_COPY_PLACEHOLDER_ID, "class": 'menu-creation-item-copy-placeholder' })
            .append($('<div>', { 'class': 'menu-creation-item part-attachment' }));
          break;
        case MenuPartType.PART_IMAGE:
          vm.$copyPlaceholder = $("<div>", { id: ITEM_COPY_PLACEHOLDER_ID, "class": 'menu-creation-item-copy-placeholder' })
            .append($('<div>', { 'class': 'menu-creation-item part-image' }));
          break;
        case MenuPartType.PART_ARROW:
          vm.$copyPlaceholder = $("<div>", { id: ITEM_COPY_PLACEHOLDER_ID, "class": 'menu-creation-item-copy-placeholder' })
            .append($('<div>', { 'class': 'menu-creation-item part-arrow' }));
          break;
        default:
          vm.$copyPlaceholder = $("<div>");
          break;
      }
      // Set more attr (highlight width, height, position)
      LayoutUtils.renderPartDOM(vm.$copyPlaceholder, partData);
      // Append to creation layout
      vm.$menuCreationLayout.append(vm.$copyPlaceholder);
      // Move placeholder on mouse move
      const layoutOffset = vm.$menuCreationLayout.offset();
      vm.layoutOffsetTop(layoutOffset.top);
      vm.layoutOffsetLeft(layoutOffset.left);
      vm.$menuCreationLayout.mousemove((event) => {
        const offsetX = event.pageX - layoutOffset.left + vm.$menuCreationLayoutContainer.scrollLeft();
        const offsetY = event.pageY - layoutOffset.top + vm.$menuCreationLayoutContainer.scrollTop();
        vm.$copyPlaceholder.css({ 'top': `${offsetY}px`, 'left': `${offsetX}px` });
        // Calculate highlight div position
        const positionTop: number = LayoutUtils.calculatePositionTop(partData.height, offsetY);
        const positionLeft: number = LayoutUtils.calculatePositionLeft(partData.width, offsetX);
        vm.renderHoveringItem(vm.$copyPlaceholder, vm.partClientId, partData.width, partData.height, positionTop, positionLeft);
      });
    }

    /**
     * Remove part
     */
    private removePart($part: JQuery) {
      const vm = this;
      const partClientId: number = Number($part.attr(KEY_DATA_ITEM_CLIENT_ID));
      delete vm.mapPartData[partClientId];
      vm.$listPart = _.filter(vm.$listPart, ($item) => Number($item.attr(KEY_DATA_ITEM_CLIENT_ID)) !== partClientId);
      $part.remove();
    }

    /**
     * Close dialog
     */
    public closeDialog() {
      const vm = this;
      vm.$window.close();
    }

    /**
     * Open preview dialog
     */
    public openPreviewDialog() {
      const vm = this;
      let $layout: JQuery = $('<div>')
        .css({ 'width': CREATION_LAYOUT_WIDTH, 'height': CREATION_LAYOUT_HEIGHT });
      for (const partClientId in vm.mapPartData) {
        $layout.append(LayoutUtils.buildPartHTML(vm.mapPartData[partClientId]));
      }
      const params = {
        fileId: vm.flowMenuFileId(),
        htmlSrc: vm.createHTMLLayout($layout),
      };
      vm.$window.modal('/view/ccg/034/b/index.xhtml', params, {
        width: Math.round(Number(window.parent.innerWidth) * 70 / 100),
        height: Math.round(Number(window.parent.innerHeight) * 80 / 100),
        resizable: true,
      });
    }

    /**
     * Save layout
     */
    public saveLayout() {
      const vm = this;
      // Save html as file
      const listPartData: PartDataModel[] = [];
      let $layout: JQuery = $('<div>')
        .css({ 'width': CREATION_LAYOUT_WIDTH, 'height': CREATION_LAYOUT_HEIGHT });
      for (const partClientId in vm.mapPartData) {
        listPartData.push(vm.mapPartData[partClientId]);
        $layout.append(LayoutUtils.buildPartHTML(vm.mapPartData[partClientId]));
      }
      vm.$blockui('grayout');
      const generateHtmlParams: any = {
        flowMenuCode: vm.flowMenuCode(),
        htmlContent: vm.createHTMLLayout($layout),
      };
      // アップロードファイルの保存及びHTMLのファイルを作成ZIPファイルとし保存する
      vm.$ajax(API.generateHtml, generateHtmlParams)
        // [After] generate html file
        .then((res: { taskId: string }) => {
          vm.flowMenuFileId(res.taskId);
          // Prepare command
          const listMenuSettingDto: MenuSettingDto[] = _.chain(listPartData)
            .filter((data: PartDataModel) => data.partType === MenuPartType.PART_MENU)
            .map((data: PartDataMenuModel) => new MenuSettingDto({
              flowMenuCode: vm.flowMenuCode(),
              column: (data.positionLeft / CELL_SIZE),
              row: (data.positionTop / CELL_SIZE),
              width: data.width,
              height: data.height,
              fontSize: data.fontSize,
              bold: data.isBold ? 1 : 0,
              horizontalPosition: data.alignHorizontal,
              verticalPosition: data.alignVertical,
              systemType: data.systemType,
              menuClassification: data.menuClassification,
              menuCode: data.menuCode,
              menuName: data.menuName,
            }))
            .value();
          const listLabelSettingDto: LabelSettingDto[] = _.chain(listPartData)
            .filter((data: PartDataModel) => data.partType === MenuPartType.PART_LABEL)
            .map((data: PartDataLabelModel) => new LabelSettingDto({
              flowMenuCode: vm.flowMenuCode(),
              column: (data.positionLeft / CELL_SIZE),
              row: (data.positionTop / CELL_SIZE),
              width: data.width,
              height: data.height,
              labelContent: data.labelContent,
              fontSize: data.fontSize,
              bold: data.isBold ? 1 : 0,
              textColor: data.textColor,
              backgroundColor: data.backgroundColor,
              horizontalPosition: data.alignHorizontal,
              verticalPosition: data.alignVertical,
            }))
            .value();
          const listLinkSettingDto: LinkSettingDto[] = _.chain(listPartData)
            .filter((data: PartDataModel) => data.partType === MenuPartType.PART_LINK)
            .map((data: PartDataLinkModel) => new LinkSettingDto({
              flowMenuCode: vm.flowMenuCode(),
              column: (data.positionLeft / CELL_SIZE),
              row: (data.positionTop / CELL_SIZE),
              width: data.width,
              height: data.height,
              linkContent: data.linkContent,
              url: data.url,
              fontSize: data.fontSize,
              bold: data.isBold ? 1 : 0,
              horizontalPosition: data.alignHorizontal,
              verticalPosition: data.alignVertical,
            }))
            .value();
          const listFileAttachmentSettingDto: FileAttachmentSettingDto[] = _.chain(listPartData)
            .filter((data: PartDataModel) => data.partType === MenuPartType.PART_ATTACHMENT)
            .map((data: PartDataAttachmentModel) => new FileAttachmentSettingDto({
              flowMenuCode: vm.flowMenuCode(),
              column: (data.positionLeft / CELL_SIZE),
              row: (data.positionTop / CELL_SIZE),
              width: data.width,
              height: data.height,
              fileId: data.fileId,
              linkContent: data.linkContent,
              fontSize: data.fontSize,
              bold: data.isBold ? 1 : 0,
              horizontalPosition: data.alignHorizontal,
              verticalPosition: data.alignVertical,
            }))
            .value();
          const listImageSettingDto: ImageSettingDto[] = _.chain(listPartData)
            .filter((data: PartDataModel) => data.partType === MenuPartType.PART_IMAGE)
            .map((data: PartDataImageModel) => new ImageSettingDto({
              flowMenuCode: vm.flowMenuCode(),
              column: (data.positionLeft / CELL_SIZE),
              row: (data.positionTop / CELL_SIZE),
              width: data.width,
              height: data.height,
              fileId: data.fileId,
              fileName: data.isFixed === 0 ? data.fileName : data.uploadedFileName,
              isFixed: data.isFixed,
            }))
            .value();
          const listArrowSettingDto: ArrowSettingDto[] = _.chain(listPartData)
            .filter((data: PartDataModel) => data.partType === MenuPartType.PART_ARROW)
            .map((data: PartDataArrowModel) => new ArrowSettingDto({
              flowMenuCode: vm.flowMenuCode(),
              column: (data.positionLeft / CELL_SIZE),
              row: (data.positionTop / CELL_SIZE),
              width: data.width,
              height: data.height,
              fileName: data.fileName,
            }))
            .value();
          const updateLayoutParams: UpdateFlowMenuLayoutCommand = new UpdateFlowMenuLayoutCommand({
            flowMenuCode: vm.flowMenuCode(),
            flowMenuLayout: new FlowMenuLayoutDto({
              fileId: res.taskId,
              menuSettings: listMenuSettingDto,
              labelSettings: listLabelSettingDto,
              linkSettings: listLinkSettingDto,
              fileAttachmentSettings: listFileAttachmentSettingDto,
              imageSettings: listImageSettingDto,
              arrowSettings: listArrowSettingDto,
            }),
          });
          return vm.$ajax(API.updateLayout, updateLayoutParams);
        })
        // [After] save layout data
        .then(() => {
          vm.$blockui('clear');
          vm.$dialog.info({ messageId: 'Msg_15' });
        })
        .fail((err) => vm.$dialog.error({ messageId: err.messageId }))
        .always(() => vm.$blockui('clear'));
    }

    /**
     * create HTML Layout
     */
    private createHTMLLayout($layout: JQuery): string {
      let htmlContent: string = `<!DOCTYPE html>`;
      htmlContent += `<html xmlns="http://www.w3.org/1999/xhtml" xmlns:ui="http://java.sun.com/jsf/facelets" xmlns:com="http://xmlns.jcp.org/jsf/component" xmlns:h="http://xmlns.jcp.org/jsf/html">`;
      htmlContent += `<head><link rel="stylesheet" type="text/css" href="/nts.uk.com.js.web/lib/nittsu/ui/style/stylesheets/base.css"></head>`;
      htmlContent += `<body>`;
      htmlContent += $layout.html();
      htmlContent += `</body>`;
      htmlContent += `</html>`;
      return htmlContent;
    }

  }

  export class LayoutUtils {

    /**
     * Start drag item from menu
     * @param item
     * @param width
     * @param height
     */
    static startDragItemFromMenu(item: JQueryUI.DraggableEventUIParams) {
      // Init size + style for dragging item
      item.helper.css({ 'opacity': '0.7' });
    }

    /**
     * On click part setting
     * @param partClientId
     */
    static onPartClickSetting($part: JQuery, visible: boolean) {
      const $partSettingPopup: JQuery = $part.find('.part-setting-popup');
      if ($partSettingPopup) {
        if (visible) {
          $partSettingPopup.css('display', 'block');
        } else {
          $partSettingPopup.css('display', 'none');
        }
      }
    }

    /**
     * Create part class
     * @param partType
     */
    static renderPartDOM($part: JQuery, partData: PartDataModel): JQuery {
      const vm = this;
      switch (partData.partType) {
        case MenuPartType.PART_MENU:
          return vm.renderPartDOMMenu($part, partData as PartDataMenuModel);
        case MenuPartType.PART_LABEL:
          return vm.renderPartDOMLabel($part, partData as PartDataLabelModel);
        case MenuPartType.PART_LINK:
          return vm.renderPartDOMLink($part, partData as PartDataLinkModel);
        case MenuPartType.PART_ATTACHMENT:
          return vm.renderPartDOMAttachment($part, partData as PartDataAttachmentModel);
        case MenuPartType.PART_IMAGE:
          return vm.renderPartDOMImage($part, partData as PartDataImageModel);
        case MenuPartType.PART_ARROW:
          return vm.renderPartDOMArrow($part, partData as PartDataArrowModel);
        default:
          return vm.renderPartDOMMenu($part, partData as PartDataMenuModel);
      }
    }

    /**
     * Render PartDataMenuModel
     * @param partData
     */
    static renderPartDOMMenu($partContainer: JQuery, partData: PartDataMenuModel): JQuery {
      const vm = this;
      $partContainer
        // Set PartData attr
        .outerWidth(partData.width)
        .outerHeight(partData.height)
        .css({
          'top': `${partData.positionTop}px`,
          'left': `${partData.positionLeft}px`,
        })
        // Update item data object
        .attr(KEY_DATA_ITEM_CLIENT_ID, partData.clientId);
      const $part = $partContainer.find('.menu-creation-item');
      $part
        // Set PartDataLabelModel attr
        .css({
          'display': 'flex',
          'justify-content': vm.getHorizontalClass(partData.alignHorizontal),
          'align-items': vm.getVerticalClass(partData.alignVertical),
        });
      // Render label
      let $menuName = $part.find('.part-menu-name');
      if (!$menuName.length) {
        $menuName = $("<span>", { 'class': 'part-menu-name' });
      }
      $menuName
        .text(partData.menuName)
        .css({
          'font-size': partData.fontSize,
          'font-weight': partData.isBold ? 'bold' : 'normal',
        })
        .addClass('hyperlink');
      $menuName.appendTo($part);
      return $partContainer;
    }

    /**
     * Render PartDataLabelModel
     * @param partData
     */
    static renderPartDOMLabel($partContainer: JQuery, partData: PartDataLabelModel): JQuery {
      const vm = this;
      $partContainer
        // Set PartData attr
        .outerWidth(partData.width)
        .outerHeight(partData.height)
        .css({
          'top': `${partData.positionTop}px`,
          'left': `${partData.positionLeft}px`,
        })
        // Update item data object
        .attr(KEY_DATA_ITEM_CLIENT_ID, partData.clientId);
      const $part = $partContainer.find('.menu-creation-item');
      $part
        // Set PartDataLabelModel attr
        .css({
          'color': partData.textColor,
          'background-color': partData.backgroundColor,
          'display': 'flex',
          'justify-content': vm.getHorizontalClass(partData.alignHorizontal),
          'align-items': vm.getVerticalClass(partData.alignVertical),
        });
      // Render label
      let $labelContent = $part.find('.part-label-content');
      if (!$labelContent.length) {
        $labelContent = $("<span>", { 'class': 'part-label-content' });
      }
      $labelContent
        .text(partData.labelContent)
        .css({
          'font-size': partData.fontSize,
          'font-weight': partData.isBold ? 'bold' : 'normal',
        });
      $labelContent.appendTo($part);
      return $partContainer;
    }

    /**
     * Render PartDataLinkModel
     * @param partData
     */
    static renderPartDOMLink($partContainer: JQuery, partData: PartDataLinkModel): JQuery {
      const vm = this;
      $partContainer
        // Set PartData attr
        .outerWidth(partData.width)
        .outerHeight(partData.height)
        .css({
          'top': `${partData.positionTop}px`,
          'left': `${partData.positionLeft}px`,
        })
        // Update item data object
        .attr(KEY_DATA_ITEM_CLIENT_ID, partData.clientId);
      const $part = $partContainer.find('.menu-creation-item');
      $part
        // Set PartDataLabelModel attr
        .css({
          'display': 'flex',
          'justify-content': vm.getHorizontalClass(partData.alignHorizontal),
          'align-items': vm.getVerticalClass(partData.alignVertical),
        });
      // Render label
      let $linkContent = $part.find('.part-link-content');
      if (!$linkContent.length) {
        $linkContent = $("<span>", { 'class': 'part-link-content' });
      }
      $linkContent
        .text(partData.linkContent || partData.url)
        .css({
          'font-size': partData.fontSize,
          'font-weight': partData.isBold ? 'bold' : 'normal',
        })
        .addClass('hyperlink');
      $linkContent.appendTo($part);
      return $partContainer;
    }

    /**
     * Render PartDataAttachmentModel
     * @param partData
     */
    static renderPartDOMAttachment($partContainer: JQuery, partData: PartDataAttachmentModel): JQuery {
      const vm = this;
      $partContainer
        // Set PartData attr
        .outerWidth(partData.width)
        .outerHeight(partData.height)
        .css({
          'top': `${partData.positionTop}px`,
          'left': `${partData.positionLeft}px`,
        })
        // Update item data object
        .attr(KEY_DATA_ITEM_CLIENT_ID, partData.clientId);
      const $part = $partContainer.find('.menu-creation-item');
      $part
        // Set PartDataLabelModel attr
        .css({
          'display': 'flex',
          'justify-content': vm.getHorizontalClass(partData.alignHorizontal),
          'align-items': vm.getVerticalClass(partData.alignVertical),
        });
      // Render label
      let $fileContent = $part.find('.part-file-content');
      if (!$fileContent.length) {
        $fileContent = $("<span>", { 'class': 'part-file-content' });
      }
      $fileContent
        .text(partData.linkContent)
        .css({
          'font-size': partData.fontSize,
          'font-weight': partData.isBold ? 'bold' : 'normal',
        })
        .addClass('hyperlink');
      $fileContent.appendTo($part);
      return $partContainer;
    }

    /**
     * Render PartDataImageModel
     * @param partData
     */
    static renderPartDOMImage($partContainer: JQuery, partData: PartDataImageModel): JQuery {
      $partContainer
        // Set PartData attr
        .outerWidth(partData.width)
        .outerHeight(partData.height)
        .css({
          'top': `${partData.positionTop}px`,
          'left': `${partData.positionLeft}px`,
          'align-items': 'center'
        })
        // Update item data object
        .attr(KEY_DATA_ITEM_CLIENT_ID, partData.clientId);
      const $part = $partContainer.find('.menu-creation-item');
      $part
        // Set PartDataLabelModel attr
        .css({
          'display': 'flex',
        });
      // Render label
      let $imageContent = $part.find('.part-image-content');
      if (!$imageContent.length) {
        $imageContent = $("<img>", { 'class': 'part-image-content' });
      }
      $imageContent
        .attr('src', (partData.isFixed === 0) ? partData.fileName : (nts.uk.request as any).liveView(partData.fileId));
      // Set image scale by original ratio
      const partRatio = partData.height / partData.width;
      const imageRatio = partData.ratio;
      if (partRatio > imageRatio) {
        $imageContent.css({
          'width': '100%',
          'height': 'auto',
        });
      } else {
        $imageContent.css({
          'width': 'auto',
          'height': '100%',
        });
      }
      $imageContent.appendTo($part);
      return $partContainer;
    }

    /**
     * Render PartDataArrowModel
     * @param partData
     */
    static renderPartDOMArrow($partContainer: JQuery, partData: PartDataArrowModel): JQuery {
      $partContainer
        // Set PartData attr
        .outerWidth(partData.width)
        .outerHeight(partData.height)
        .css({
          'top': `${partData.positionTop}px`,
          'left': `${partData.positionLeft}px`,
        })
        // Update item data object
        .attr(KEY_DATA_ITEM_CLIENT_ID, partData.clientId);
      const $part = $partContainer.find('.menu-creation-item');
      $part
        // Set PartDataLabelModel attr
        .css({
          'display': 'flex',
        });
      // Render label
      let $arrowContent = $part.find('.part-arrow-content');
      if (!$arrowContent.length) {
        $arrowContent = $("<img>", { 'class': 'part-arrow-content' });
      }
      $arrowContent.attr('src', partData.fileSrc);
      // Set image scale by original ratio
      const partRatio = partData.height / partData.width;
      const imageRatio = 1;
      if (partRatio > imageRatio) {
        $arrowContent.css({
          'width': '100%',
          'height': 'auto',
        });
      } else {
        $arrowContent.css({
          'width': 'auto',
          'height': '100%',
        });
      }
      $arrowContent.appendTo($part);
      return $partContainer;
    }

    /**
     * getHorizontalClass
     */
    static getHorizontalClass(alignHorizontal: number): string {
      let horizontalPosition: string = null;
      switch (alignHorizontal) {
        case HorizontalAlign.LEFT:
          horizontalPosition = 'flex-start';
          break;
        case HorizontalAlign.MIDDLE:
          horizontalPosition = 'center';
          break;
        case HorizontalAlign.RIGHT:
          horizontalPosition = 'flex-end';
          break;
        default:
          horizontalPosition = 'flex-start';
          break;
      }
      return horizontalPosition;
    }

    /**
     * getVerticalClass
     */
    static getVerticalClass(alignVertical: number): string {
      let verticalPosition: string = null;
      switch (alignVertical) {
        case VerticalAlign.TOP:
          verticalPosition = 'flex-start';
          break;
        case VerticalAlign.CENTER:
          verticalPosition = 'center';
          break;
        case VerticalAlign.BOTTOM:
          verticalPosition = 'flex-end';
          break;
        default:
          verticalPosition = 'flex-start';
          break;
      }
      return verticalPosition;
    }

    static calculatePositionTop(itemHeight: number, positionTop: number): number {
      const maxPositionTop = CREATION_LAYOUT_HEIGHT - itemHeight;
      return (positionTop > 0)
        ? ((positionTop + itemHeight) <= CREATION_LAYOUT_HEIGHT ? Math.round(positionTop / CELL_SIZE) : maxPositionTop / CELL_SIZE) * CELL_SIZE
        : 0;
    }

    static calculatePositionLeft(itemWidth: number, positionLeft: number): number {
      const maxPositionLeft = CREATION_LAYOUT_WIDTH - itemWidth;
      return (positionLeft > 0)
        ? ((positionLeft + itemWidth) <= CREATION_LAYOUT_WIDTH ? Math.round(positionLeft / CELL_SIZE) : maxPositionLeft / CELL_SIZE) * CELL_SIZE
        : 0;
    }

    /**
     * Detects if two item part are colliding
     * https://gist.github.com/jtsternberg/c272d7de5b967cec2d3d
     * @param partData1
     * @param partData2
     */
    static isItemOverlapping(partData1: PartDataModel, partData2: PartDataModel): boolean {
      if (partData1.clientId === partData2.clientId) {
        return false;
      }
      // Part data 1
      const partData1DistanceFromTop = partData1.positionTop + partData1.height;
      const partData1DistanceFromLeft = partData1.positionLeft + partData1.width;
      // Part data 2
      const partData2DistanceFromTop = partData2.positionTop + partData2.height;
      const partData2DistanceFromLeft = partData2.positionLeft + partData2.width;

      const notColliding = (partData1DistanceFromTop <= partData2.positionTop
        || partData1.positionTop >= partData2DistanceFromTop
        || partData1DistanceFromLeft <= partData2.positionLeft
        || partData1.positionLeft >= partData2DistanceFromLeft);

      // Return whether it IS colliding
      return !notColliding;
    }

    /**
     * Get part size by type
     * @param partType
     */
    static getPartSize(partType: string): PartSize {
      switch (partType) {
        case MenuPartType.PART_MENU:
          // 4 x 2 cell
          return new PartSize({ width: 160, height: 80 });
        case MenuPartType.PART_LABEL:
          // 4 x 2 cell
          return new PartSize({ width: 160, height: 80 });
        case MenuPartType.PART_LINK:
          // 4 x 2 cell
          return new PartSize({ width: 160, height: 80 });
        case MenuPartType.PART_ATTACHMENT:
          // 4 x 2 cell
          return new PartSize({ width: 160, height: 80 });
        case MenuPartType.PART_IMAGE:
          // 2 x 2 cell
          return new PartSize({ width: 80, height: 80 });
        case MenuPartType.PART_ARROW:
          // 2 x 2 cell
          return new PartSize({ width: 80, height: 80 });
        default:
          // 4 x 2 cell
          return new PartSize({ width: 160, height: 80 });
      }
    }

    /**
     * build PartHTML from PartData
     */
    static buildPartHTML(partData: PartDataModel): JQuery {
      let $partHTML = null;
      switch (partData.partType) {
        case MenuPartType.PART_MENU:
          const partDataMenuModel: PartDataMenuModel = (partData as PartDataMenuModel);
          const $partMenuHTML: JQuery = $('<a>', { 'href': `${location.origin}${partDataMenuModel.menuUrl}`, 'target': '_blank' })
            .text(partDataMenuModel.menuName)
            .css({
              'font-size': `${partDataMenuModel.fontSize}px`,
              'font-weight': partDataMenuModel.isBold ? 'bold' : 'normal',
              'color': '#0066CC',
              'text-decoration': 'underline',
              'cursor': 'pointer',
            });
          $partHTML = $("<div>")
            .css({
              'position': 'absolute',
              'top': `${partDataMenuModel.positionTop}px`,
              'left': `${partDataMenuModel.positionLeft}px`,
              'width': `${partDataMenuModel.width}px`,
              'height': `${partDataMenuModel.height}px`,
              'display': 'flex',
              'align-items': LayoutUtils.getVerticalClass(partDataMenuModel.alignVertical),
              'justify-content': LayoutUtils.getHorizontalClass(partDataMenuModel.alignVertical),
              'overflow': 'hidden',
              'text-overflow': 'ellipsis',
              'word-break': 'break-word',
            })
            .append($partMenuHTML);
          break;
        case MenuPartType.PART_LABEL:
          const partDataLabelModel: PartDataLabelModel = (partData as PartDataLabelModel);
          const $partLabelHTML: JQuery = $('<span>')
            .text(partDataLabelModel.labelContent)
            .css({
              'font-size': `${partDataLabelModel.fontSize}px`,
              'font-weight': partDataLabelModel.isBold ? 'bold' : 'normal',
              'color': partDataLabelModel.textColor,
            });
          $partHTML = $("<div>")
            .css({
              'position': 'absolute',
              'top': `${partDataLabelModel.positionTop}px`,
              'left': `${partDataLabelModel.positionLeft}px`,
              'width': `${partDataLabelModel.width}px`,
              'height': `${partDataLabelModel.height}px`,
              'display': 'flex',
              'align-items': LayoutUtils.getVerticalClass(partDataLabelModel.alignVertical),
              'justify-content': LayoutUtils.getHorizontalClass(partDataLabelModel.alignVertical),
              'overflow': 'hidden',
              'text-overflow': 'ellipsis',
              'word-break': 'break-word',
              'background-color': partDataLabelModel.backgroundColor,
            })
            .append($partLabelHTML);
          break;
        case MenuPartType.PART_LINK:
          const partDataLinkModel: PartDataLinkModel = (partData as PartDataLinkModel);
          const $partLinkHTML: JQuery = $('<a>', { 'href': partDataLinkModel.url, 'target': '_blank' })
            .text(partDataLinkModel.linkContent || partDataLinkModel.url)
            .css({
              'font-size': `${partDataLinkModel.fontSize}px`,
              'font-weight': partDataLinkModel.isBold ? 'bold' : 'normal',
              'color': '#0066CC',
              'text-decoration': 'underline',
              'cursor': 'pointer',
            });
          $partHTML = $("<div>")
            .css({
              'position': 'absolute',
              'top': `${partDataLinkModel.positionTop}px`,
              'left': `${partDataLinkModel.positionLeft}px`,
              'width': `${partDataLinkModel.width}px`,
              'height': `${partDataLinkModel.height}px`,
              'display': 'flex',
              'align-items': LayoutUtils.getVerticalClass(partDataLinkModel.alignVertical),
              'justify-content': LayoutUtils.getHorizontalClass(partDataLinkModel.alignVertical),
              'overflow': 'hidden',
              'text-overflow': 'ellipsis',
              'word-break': 'break-word',
            })
            .append($partLinkHTML);
          break;
        case MenuPartType.PART_ATTACHMENT:
          const partDataAttachmentModel: PartDataAttachmentModel = (partData as PartDataAttachmentModel);
          const fileLink: string = `${location.origin}/nts.uk.com.web/webapi/shr/infra/file/storage/get/${partDataAttachmentModel.fileId}`;
          const $partAttachmentHTML: JQuery = $('<a>', { 'href': fileLink, 'target': '_blank' })
            .text(partDataAttachmentModel.linkContent || partDataAttachmentModel.fileName)
            .css({
              'font-size': `${partDataAttachmentModel.fontSize}px`,
              'font-weight': partDataAttachmentModel.isBold ? 'bold' : 'normal',
              'color': '#0066CC',
              'text-decoration': 'underline',
              'cursor': 'pointer',
            });
          $partHTML = $("<div>")
            .css({
              'position': 'absolute',
              'top': `${partDataAttachmentModel.positionTop}px`,
              'left': `${partDataAttachmentModel.positionLeft}px`,
              'width': `${partDataAttachmentModel.width}px`,
              'height': `${partDataAttachmentModel.height}px`,
              'display': 'flex',
              'align-items': LayoutUtils.getVerticalClass(partDataAttachmentModel.alignVertical),
              'justify-content': LayoutUtils.getHorizontalClass(partDataAttachmentModel.alignVertical),
              'overflow': 'hidden',
              'text-overflow': 'ellipsis',
              'word-break': 'break-word',
            })
            .append($partAttachmentHTML);
          break;
        case MenuPartType.PART_IMAGE:
          const partDataImageModel: PartDataImageModel = (partData as PartDataImageModel);
          const $partImageHTML: JQuery = $('<img>', {
            'src': partDataImageModel.isFixed === 0
              ? partDataImageModel.fileName
              : (nts.uk.request as any).liveView(partDataImageModel.fileId)
          })
            .css({
              'width': (partDataImageModel.width > partDataImageModel.height) ? 'auto' : '100%',
              'height': (partDataImageModel.width > partDataImageModel.height) ? '100%' : 'auto',
            });
          $partHTML = $("<div>")
            .css({
              'position': 'absolute',
              'top': `${partDataImageModel.positionTop}px`,
              'left': `${partDataImageModel.positionLeft}px`,
              'width': `${partDataImageModel.width}px`,
              'height': `${partDataImageModel.height}px`,
              'display': 'flex',
              'align-items': 'center',
              'justify-content': 'center',
              'overflow': 'hidden',
            })
            .append($partImageHTML);
          break;
        case MenuPartType.PART_ARROW:
          const partDataArrowModel: PartDataArrowModel = (partData as PartDataArrowModel);
          const $partArrowHTML: JQuery = $('<img>', { 'src': partDataArrowModel.fileSrc })
            .css({
              'width': (partDataArrowModel.width > partDataArrowModel.height) ? 'auto' : '100%',
              'height': (partDataArrowModel.width > partDataArrowModel.height) ? '100%' : 'auto',
            });
          $partHTML = $("<div>")
            .css({
              'position': 'absolute',
              'top': `${partDataArrowModel.positionTop}px`,
              'left': `${partDataArrowModel.positionLeft}px`,
              'width': `${partDataArrowModel.width}px`,
              'height': `${partDataArrowModel.height}px`,
              'display': 'flex',
              'align-items': 'center',
              'justify-content': 'center',
              'overflow': 'hidden',
            })
            .append($partArrowHTML);
          break;
        default:
          // TODO
          $partHTML = $("<div>")
            .css({
              'position': 'absolute',
              'top': `${partData.positionTop}px`,
              'left': `${partData.positionLeft}px`,
              'width': `${partData.width}px`,
              'height': `${partData.height}px`,
            });
          break;
      }
      return $partHTML;
    }
  }

  export class PartSize {
    width: number;
    height: number;

    constructor(init?: Partial<PartSize>) {
      $.extend(this, init);
    }
  }

  export enum MenuPartType {
    PART_MENU = '1',
    PART_LABEL = '2',
    PART_LINK = '3',
    PART_ATTACHMENT = '4',
    PART_IMAGE = '5',
    PART_ARROW = '6',
  }

  export enum HorizontalAlign {
    LEFT = 0,
    MIDDLE = 1,
    RIGHT = 2,
  }

  export enum VerticalAlign {
    TOP = 0,
    CENTER = 1,
    BOTTOM = 2,
  }

  export class PartDataModel {
    clientId: number;
    width: number;
    height: number;
    minWidth: number;
    minHeight: number;
    partType: string;
    positionTop: number;
    positionLeft: number;

    constructor(init?: Partial<PartDataModel>) {
      $.extend(this, init);
    }
  }

  export class PartDataMenuModel extends PartDataModel {
    alignHorizontal: number = HorizontalAlign.MIDDLE;
    alignVertical: number = VerticalAlign.CENTER;
    menuCode: string = null;
    menuName: string = "";
    menuClassification: number = 0;
    systemType: number = 0;
    fontSize: number = 11;
    isBold: boolean = true;
    menuUrl: string = null;

    constructor(init?: Partial<PartDataMenuModel>) {
      super(init);
      $.extend(this, init);
    }
  }

  export class PartDataLabelModel extends PartDataModel {
    // Default data
    alignHorizontal: number = HorizontalAlign.LEFT;
    alignVertical: number = VerticalAlign.CENTER;
    labelContent: string = '';
    fontSize: number = 11;
    isBold: boolean = true;
    textColor: string = '#000000';
    backgroundColor: string = '#ffffff';

    constructor(init?: Partial<PartDataLabelModel>) {
      super(init);
      $.extend(this, init);
    }
  }

  export class PartDataLinkModel extends PartDataModel {
    // Default data
    alignHorizontal: number = HorizontalAlign.LEFT;
    alignVertical: number = VerticalAlign.CENTER;
    url: string = null;
    linkContent: string = '';
    fontSize: number = 11;
    isBold: boolean = true;

    constructor(init?: Partial<PartDataLinkModel>) {
      super(init);
      $.extend(this, init);
    }
  }

  export class PartDataAttachmentModel extends PartDataModel {
    // Default data
    alignHorizontal: number = HorizontalAlign.LEFT;
    alignVertical: number = VerticalAlign.CENTER;
    fileId: string = null;
    fileSize: number = 0;
    fileName: string = null;
    linkContent: string = '';
    fileLink: string = null;
    fontSize: number = 11;
    isBold: boolean = true;

    constructor(init?: Partial<PartDataAttachmentModel>) {
      super(init);
      $.extend(this, init);
    }
  }

  export class PartDataImageModel extends PartDataModel {
    // Default data
    fileId: string = null;
    fileName: string = null;
    uploadedFileName: string = null;
    uploadedFileSize: number = 0;
    isFixed: number = 0;
    ratio: number = 1;

    constructor(init?: Partial<PartDataImageModel>) {
      super(init);
      $.extend(this, init);
    }
  }

  export class PartDataArrowModel extends PartDataModel {
    // Default data
    fileName: string = null;
    fileSrc: string = null

    constructor(init?: Partial<PartDataArrowModel>) {
      super(init);
      $.extend(this, init);
    }
  }

  class UpdateFlowMenuLayoutCommand {
    flowMenuCode: string;
    flowMenuLayout: FlowMenuLayoutDto;

    constructor(init?: Partial<UpdateFlowMenuLayoutCommand>) {
      $.extend(this, init);
    }
  }

  class FlowMenuLayoutDto {
    fileId: string;
    flowMenuCode: string;
    flowMenuName: string;
    menuSettings: MenuSettingDto[];
    labelSettings: LabelSettingDto[];
    linkSettings: LinkSettingDto[];
    fileAttachmentSettings: FileAttachmentSettingDto[];
    imageSettings: ImageSettingDto[];
    arrowSettings: ArrowSettingDto[];

    constructor(init?: Partial<FlowMenuLayoutDto>) {
      $.extend(this, init);
    }
  }

  export class MenuSettingDto {
    flowMenuCode: string;
    column: number;   // pixel / cellsize
    row: number;      // pixel / cellsize
    width: number;    // pixel
    height: number;    // pixel
    fontSize: number;
    bold: number;
    horizontalPosition: number;
    verticalPosition: number;
    systemType: number;
    menuClassification: number;
    menuCode: string;
    menuName: string;

    constructor(init?: Partial<MenuSettingDto>) {
      $.extend(this, init);
    }
  }

  export class LabelSettingDto {
    flowMenuCode: string;
    column: number;   // pixel / cellsize
    row: number;      // pixel / cellsize
    width: number;    // pixel
    height: number;    // pixel
    labelContent: string;
    fontSize: number;
    bold: number;
    textColor: string;
    backgroundColor: string;
    horizontalPosition: number;
    verticalPosition: number;

    constructor(init?: Partial<LabelSettingDto>) {
      $.extend(this, init);
    }
  }

  export class LinkSettingDto {
    flowMenuCode: string;
    column: number;   // pixel / cellsize
    row: number;      // pixel / cellsize
    width: number;    // pixel
    height: number;    // pixel
    linkContent: string;
    url: string;
    fontSize: number;
    bold: number;
    horizontalPosition: number;
    verticalPosition: number;

    constructor(init?: Partial<LinkSettingDto>) {
      $.extend(this, init);
    }
  }

  export class FileAttachmentSettingDto {
    flowMenuCode: string;
    column: number;   // pixel / cellsize
    row: number;      // pixel / cellsize
    width: number;    // pixel
    height: number;    // pixel
    fileId: string;
    linkContent: string;
    fontSize: number;
    bold: number;
    horizontalPosition: number;
    verticalPosition: number;

    constructor(init?: Partial<FileAttachmentSettingDto>) {
      $.extend(this, init);
    }
  }

  export class ImageSettingDto {
    flowMenuCode: string;
    column: number;   // pixel / cellsize
    row: number;      // pixel / cellsize
    width: number;    // pixel
    height: number;    // pixel
    fileId: string;
    fileName: string;
    isFixed: number;

    constructor(init?: Partial<ImageSettingDto>) {
      $.extend(this, init);
    }
  }

  export class ArrowSettingDto {
    flowMenuCode: string;
    column: number;   // pixel / cellsize
    row: number;      // pixel / cellsize
    width: number;    // pixel
    height: number;
    fileName: string;

    constructor(init?: Partial<ArrowSettingDto>) {
      $.extend(this, init);
    }
  }

}
<template>
  <div id="app_uk_mobile">
    <div class="wrapper">
      <div
        class="container-fluid px-3"
        style="overflow: hidden; padding-top: 16px"
      >
        <div>
          <div
            role="alert"
            class="border border-warning rounded p-1 mt-2 alarm-message"
            style="display: block; margin-top: 0px !important"
          >
            <div style="display: flex">
              <i class="text-danger m-2 fas fa-exclamation-triangle fa-"></i
              ><span style="align-self: center"
                >{{alarmMsg}}</span
              >
            </div>
            <!---->
          </div>
          <!---->
        </div>
        <div style="padding-top: 10px;" >
          <calendar v-bind:params="{datas: dataCalendar}"  @dataChangeMonth="dataChange($event)" @dataFromComponent="dataFromChild($event)"></calendar>
        </div>
        <div
          style="
            position: fixed;
            width: -webkit-fill-available;
            height: 45px;
            padding: 0px 10;
            background-color: white;
            left: 0;
            bottom: 0;
            padding: 4px 10px;
          "
        >
          <button
            v-if="isCurrentMonth == true" 
            v-click="register"
            type="button"
            class="btn btn-success btn-block"
          >
            {{'KSUS02_2' | i18n}}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

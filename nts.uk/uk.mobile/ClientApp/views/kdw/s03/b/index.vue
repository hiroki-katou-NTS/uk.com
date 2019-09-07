<template>
<div class="kdws03b">
  <div class="modal-header uk-bg-white-smoke pl-0 pr-0 rounded-0">
    <div class="row uk-text-quote w-100 ml-0 mr-0" v-on:click="$close()">
      <div class="col-1 pl-1"><i class="fas fa-arrow-left"></i></div>
      <div class="col-6 pl-0 pr-2"><span>{{ params.employeeName }}</span></div>
      <div class="col-5 pl-0 pr-0"><span>{{ params.date  | date('YYYY年MM月DD日')}}</span></div>
    </div>
  </div>
  <div class="row">
    <div class="accordion w-100">
      <div class="card border-0 pl-0">
        <div class="card-header pl-0 pr-0 uk-bg-light-coral">
          <button class="btn btn-link" type="button">{{'KDWS03_35' | i18n}}</button>
        </div>
        <div class="collapse uk-bg-light-coral">
          <div class="card-body">
            Content of group Item #1<br>
            Elements
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="row uk-bg-light-coral mb-4">
    <div class="btn w-100 text-left" v-on:click="openDScreen">
      <div class="col-11 pl-0 pr-0 d-inline-block uk-text-quote">
        {{'KDWS03_36' | i18n}}
      </div>
      <div class="d-inline-block">
        <h4 class="fas fa-angle-right uk-text-quote font-weight-bold align-text-top mb-0"></h4>
      </div>
    </div>
  </div>
  <div v-for="rowData in params.data" v-bind:key="rowData.key">
    <div class="row" v-if="getItemTypeCode(rowData)==itemType.TimePoint">
      <div class="col-12">
        <div class="row pl-2 mb-1">{{ getItemText(rowData) }}</div>
        <div class="row">
          <div class="col-2"></div>
          <div class="col-9 pl-0 pr-0"><nts-time-editor class="mb-3" v-model="rowData.value" time-input-type="time-point" /></div>
        </div>
      </div>
    </div>
    <div class="row" v-if="getItemTypeCode(rowData)==itemType.InputNumber">
      <div class="col-12">
        <div class="row pl-2 mb-1">{{ getItemText(rowData) }}</div>
        <div class="row">
          <div class="col-2"></div>
          <div class="col-9 pl-0 pr-0"><nts-number-editor class="mb-3" v-model="rowData.value" /></div>
        </div>
      </div>
    </div>
    <div class="row" v-if="getItemTypeCode(rowData)==itemType.InputString">
      <div class="col-12">
        <div class="row pl-2 mb-1">{{ getItemText(rowData) }}</div>
        <div class="row">
          <div class="col-2"></div>
          <div class="col-9 pl-0 pr-0"><nts-text-editor class="mb-3" v-model="rowData.value" /></div>
        </div>
      </div>
    </div>
    <div class="row" v-if="getItemTypeCode(rowData)==itemType.TimeWithDay">
      <div class="col-12">
        <div class="row pl-2 mb-1">{{ getItemText(rowData) }}</div>
        <div class="row">
          <div class="col-2"></div>
          <div class="col-9 pl-0 pr-0"><nts-time-editor class="mb-3" v-model="rowData.value" time-input-type="time-with-day" /></div>
        </div>
      </div>
    </div>
  </div>
  <div class="row mt-4">
    <div class="col-3"></div>
    <div>
      <nts-checkbox v-model="checked1s" v-bind:value="2">{{'KDWS03_37' | i18n}} </nts-checkbox>
    </div>
  </div>
  <div class="card invisible">
    <div class="card-body">
      Hidden Content
    </div>
  </div>
  <div class="btn fixed-bottom modal-dialog dropback uk-bg-process text-center">
    {{'KDWS03_38' | i18n}}
  </div>
</div>
</template>
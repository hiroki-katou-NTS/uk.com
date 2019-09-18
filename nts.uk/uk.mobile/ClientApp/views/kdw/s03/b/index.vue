<template>
<div class="kdws03b">
  <div class="modal-header uk-bg-white-smoke pl-0 pr-0 rounded-0">
    <div class="row uk-text-quote w-100 ml-0 mr-0" v-on:click="$close()">
      <div class="col-1 pl-1"><i class="fas fa-arrow-left"></i></div>
      <div class="col-6 pl-0 pr-2"><span>{{ params.employeeName }}</span></div>
      <div class="col-5 pl-0 pr-0"><span>{{ params.date  | date('YYYY年MM月DD日') }}</span></div>
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
  <div v-for="(rowData, rowDataKey) in params.data" v-bind:key="rowDataKey">
    <!-- InputStringCode -->
    <!--
    <div class="row" v-if="getItemTypeCode(rowData)==itemType.InputStringCode">
      <div class="col-12">
        <div class="row pl-2 mb-1">{{ getItemText(rowData) }}</div>
        <div class="row">
          <div class="col-2"></div>
          <div class="col-9 pl-0 pr-0"><nts-text-editor class="mb-3" v-model="rowData.value" /></div>
        </div>
      </div>
    </div>
    -->
    <!-- ButtonDialog -->
    <div class="row" v-if="rowData.getItemType==itemType.InputStringCode || rowData.getItemType==itemType.ButtonDialog">
      <div class="col-12" v-if="rowData.getItemMasterType==masterType.KDLS02_WorkType ||
                            rowData.getItemMasterType==masterType.KDLS01_WorkTime || rowData.getItemMasterType==masterType.CDLS08_WorkPlace">
        <div class="row pl-2 mb-1">{{ rowData.getItemText }}</div>
        <div class="row mb-1">
          <div class="col-2 p-1 text-right">
            <i class="fas fa-exclamation-circle align-bottom text-danger" v-if="rowData.getColorCode=='ERROR'"></i>
            <i class="fas fa-exclamation-triangle align-bottom text-danger" v-if="rowData.getColorCode=='ALARM'"></i>
          </div>
          <div class="col-9 pl-0 pr-0">
            <div class="row">
              <div class="w-100">
                <div class="col-9 d-inline-block align-middle"><h4><span class="badge badge-secondary">{{ rowData.value }}</span></h4></div>
                <div class="d-inline-block">
                  <button type="button" class="btn btn-secondary" v-on:click="openDialog(rowData, rowData.getItemMasterType)">{{'KDWS03_71' | i18n}}</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-12" v-else>
        <div class="row pl-2 mb-1">{{ rowData.getItemText }}</div>
        <div class="row">
          <div class="col-2 p-1 text-right">
            <i class="fas fa-exclamation-circle align-bottom text-danger" v-if="rowData.getColorCode=='ERROR'"></i>
            <i class="fas fa-exclamation-triangle align-bottom text-danger" v-if="rowData.getColorCode=='ALARM'"></i>
          </div>
          <div class="col-9 pl-0 pr-0">
            <nts-dropdown v-if="rowData.getItemMasterType==masterType.KDLS10_ServicePlace" v-model="rowData.value0">
              <option v-for="(item, k) in masterData.servicePlace" v-bind:key="k" :value="item.value0">
                {{item.value0}} &nbsp;&nbsp;&nbsp;  {{item.value}}
              </option>
            </nts-dropdown>
            <nts-dropdown v-if="rowData.getItemMasterType==masterType.KDLS32_Reason" v-model="rowData.value0">
              <option v-for="(item, k) in masterData.reason" v-bind:key="k" :value="item.value0">
                {{item.value0}} &nbsp;&nbsp;&nbsp;  {{item.value}}
              </option>
            </nts-dropdown>
            <nts-dropdown v-if="rowData.getItemMasterType==masterType.KCPS02_Classification" v-model="rowData.value0">
              <option v-for="(item, k) in masterData.classification" v-bind:key="k" :value="item.value0">
                {{item.value0}} &nbsp;&nbsp;&nbsp;  {{item.value}}
              </option>
            </nts-dropdown>
            <nts-dropdown v-if="rowData.getItemMasterType==masterType.KCPS03_Possition" v-model="rowData.value0">
              <option v-for="(item, k) in masterData.possition" v-bind:key="k" :value="item.value0">
                {{item.value0}} &nbsp;&nbsp;&nbsp;  {{item.value}}
              </option>
            </nts-dropdown>
            <nts-dropdown v-if="rowData.getItemMasterType==masterType.KCPS01_Employment" v-model="rowData.value0">
              <option v-for="(item, k) in masterData.employment" v-bind:key="k" :value="item.value0">
                {{item.value0}} &nbsp;&nbsp;&nbsp;  {{item.value}}
              </option>
            </nts-dropdown>
            <nts-dropdown v-if="rowData.getItemMasterType==masterType.KCP001_BusinessType" v-model="rowData.value0">
              <option v-for="(item, k) in masterData.businessType" v-bind:key="k" :value="item.value0">
                {{item.value0}} &nbsp;&nbsp;&nbsp;  {{item.value}}
              </option>
            </nts-dropdown>
          </div>
        </div>
      </div>
    </div>
    <!-- InputNumber -->
    <div class="row" v-if="rowData.getItemType==itemType.InputNumber">
      <div class="col-12">
        <div class="row pl-2 mb-1">{{ rowData.getItemText }}</div>
        <div class="row">
          <div class="col-2 p-1 text-right">
            <i class="fas fa-exclamation-circle align-bottom text-danger" v-if="rowData.getColorCode=='ERROR'"></i>
            <i class="fas fa-exclamation-triangle align-bottom text-danger" v-if="rowData.getColorCode=='ALARM'"></i>
          </div>
          <div class="col-9 pl-0 pr-0"><nts-number-editor class="mb-3" v-model="rowData.value" /></div>
        </div>
      </div>
    </div>
    <!-- InputMoney -->
    <div class="row" v-if="rowData.getItemType==itemType.InputMoney">
    </div>
    <!-- ComboBox -->
    <div class="row" v-if="rowData.getItemType==itemType.ComboBox">
      <div class="col-12">
        <div class="row pl-2 mb-1">{{ rowData.getItemText }}</div>
        <div class="row">
          <div class="col-2 p-1 text-right">
            <i class="fas fa-exclamation-circle align-bottom text-danger" v-if="rowData.getColorCode=='ERROR'"></i>
            <i class="fas fa-exclamation-triangle align-bottom text-danger" v-if="rowData.getColorCode=='ALARM'"></i>
          </div>
          <div class="col-9 pl-0 pr-0">
            <nts-dropdown v-if="rowData.getItemMasterType==masterType.DoWork" v-model="rowData.value0">
              <option v-for="(item, k) in masterData.lstDoWork" v-bind:key="k" :value="item.value0">
                {{item.value0}} &nbsp;&nbsp;&nbsp;  {{item.value}}
              </option>
            </nts-dropdown>
            <nts-dropdown v-if="rowData.getItemMasterType==masterType.Calc && rowData.isSpecCalcLst" v-model="rowData.value0">
              <option v-for="(item, k) in masterData.lstCalc" v-bind:key="k" :value="item.value0">
                {{item.value0}} &nbsp;&nbsp;&nbsp;  {{item.value}}
              </option>
            </nts-dropdown>
            <nts-dropdown v-if="rowData.getItemMasterType==masterType.Calc && !rowData.isSpecCalcLst" v-model="rowData.value0">
              <option v-for="(item, k) in masterData.lstCalcCompact" v-bind:key="k" :value="item.value0">
                {{item.value0}} &nbsp;&nbsp;&nbsp;  {{item.value}}
              </option>
            </nts-dropdown>
            <nts-dropdown v-if="rowData.getItemMasterType==masterType.ReasonGoOut" v-model="rowData.value0">
              <option v-for="(item, k) in masterData.lstReasonGoOut" v-bind:key="k" :value="item.value0">
                {{item.value0}} &nbsp;&nbsp;&nbsp;  {{item.value}}
              </option>
            </nts-dropdown>
            <nts-dropdown v-if="rowData.getItemMasterType==masterType.TimeLimit" v-model="rowData.value0">
              <option v-for="(item, k) in masterData.lstTimeLimit" v-bind:key="k" :value="item.value0">
                {{item.value0}} &nbsp;&nbsp;&nbsp;  {{item.value}}
              </option>
            </nts-dropdown>
          </div>
        </div>
      </div>
    </div>
    <!-- Time -->
    <div class="row" v-if="rowData.getItemType==itemType.Time">
      <div class="col-12">
        <div class="row pl-2 mb-1">{{ rowData.getItemText }}</div>
        <div class="row">
          <div class="col-2 p-1 text-right">
            <i class="fas fa-exclamation-circle align-bottom text-danger" v-if="rowData.getColorCode=='ERROR'"></i>
            <i class="fas fa-exclamation-triangle align-bottom text-danger" v-if="rowData.getColorCode=='ALARM'"></i>
          </div>
          <div class="col-9 pl-0 pr-0">
            <nts-time-editor class="mb-3" v-if="rowData.key=='A532'" v-model="screenData.A532" time-input-type="time-duration" />
            <nts-time-editor class="mb-3" v-else v-model="rowData.value" time-input-type="time-duration" />
          </div>
        </div>
      </div>
    </div>
    <!-- TimeWithDay -->
    <div class="row" v-if="rowData.getItemType==itemType.TimeWithDay">
      <div class="col-12">
        <div class="row pl-2 mb-1">{{ rowData.getItemText }}</div>
        <div class="row">
          <div class="col-2 p-1 text-right">
            <i class="fas fa-exclamation-circle align-bottom text-danger" v-if="rowData.getColorCode=='ERROR'"></i>
            <i class="fas fa-exclamation-triangle align-bottom text-danger" v-if="rowData.getColorCode=='ALARM'"></i>
          </div>
          <div class="col-9 pl-0 pr-0">
            <nts-time-editor class="mb-3" v-model="rowData.value" time-input-type="time-with-day" />
          </div>
        </div>
      </div>
    </div>
    <!-- InputStringChar -->
    <div class="row" v-if="rowData.getItemType==itemType.InputStringChar">
      <div class="col-12">
        <div class="row pl-2 mb-1">{{ rowData.getItemText }}</div>
        <div class="row">
          <div class="col-2 p-1 text-right">
            <i class="fas fa-exclamation-circle align-bottom text-danger" v-if="rowData.getColorCode=='ERROR'"></i>
            <i class="fas fa-exclamation-triangle align-bottom text-danger" v-if="rowData.getColorCode=='ALARM'"></i>
          </div>
          <div class="col-9 pl-0 pr-0"><nts-text-editor class="mb-3" v-model="rowData.value" /></div>
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
  <div class="fixed-bottom text-center register">
    <button type="button" class="btn btn-success btn-block" v-bind:disabled="!$valid" v-on:click="register()">{{'KDWS03_38' | i18n}}</button>
  </div>
  
</div>
</template>
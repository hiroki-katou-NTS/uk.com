<template>
  <div class="kafs02a">
    <div>
      <kafs00-a
        v-if="kaf000_A_Params != null"
        v-bind:params="kaf000_A_Params"
      />
    </div>

    <div v-if="!$valid || !isValidateAll" class="card bg-danger top-alert uk-text-danger topError mb-1">
              <button class="btn btn-link uk-text-danger">
                <i class="fa fa-exclamation-circle" aria-hidden="true" ></i>
                {{ 'KAFS07_1' | i18n }}
              </button>
    </div>

    <div>
      <kafs00-b
        v-if="kaf000_B_Params != null"
        v-bind:params="kaf000_B_Params"
        v-on:kaf000BChangeDate="kaf000BChangeDate"
        v-on:kaf000BChangePrePost="kaf000BChangePrePost"
        v-on:kafs00BValid="kafs00BValid"
      />
    </div>
    <!-- A3 -->
    <!-- <div v-if="(mode && (condition5 || condition4)) || (!mode && (workHourLst.length > 0 || tempWorkHourLst.length > 0))"> -->
    <div v-if="dispWorkTemp">
      <div class="card card-label">
        <div class="card-header uk-bg-accordion mt-2 mb-n2">
          <span>{{ "KAFS02_3" | i18n }}</span>
        </div>

        <!-- workHour -->
        <div v-if="(mode && condition5) || (!mode && workHourLst.length > 0)">
          <div v-for="(itemWH, index) in workHourLst" :key="itemWH.frame">
            <template v-if="index < workHourLstNumber">
              <div class="row mt-3" v-if="condition1(itemWH)">
                <div class="col-6">{{ itemWH.title | i18n }}</div>
                <!-- A3_2 -->
                <div class="col-6 text-right" style="font-size: 90%" v-if="condition2"><kafs00subp3 v-bind:params="itemWH.actualHours" /></div>
              </div>
              <!-- A3_3 -->
              <div style="color: red; font-size: 90%" v-if="condition2 && itemWH.errorMsg !== null && condition1(itemWH)">{{ itemWH.errorMsg }}</div>
              <!-- A3_1 -->
              <div class="card-body" v-if="condition1(itemWH)">
                <nts-time-range-input class="mb-1" v-model="itemWH.workHours" v-bind:showTile="false" v-bind:disabled="checkboxWH.filter((x) => x === itemWH.frame).length > 0 && condition2" />
                <div class="mt-1 mb-1">
                  <template v-if="false"> <!-- condition14 --> 
                    <!-- A3_13 -->
                    <span class="uk-text-dark-gray fs-smaller">{{ "KAFS02_33" | i18n }}</span>
                    <!-- A3_14 -->
                    <button type="button" class="btn btn-selection"
                      :disabled="checkboxWH.filter((x) => x === itemWH.frame).length > 0 && condition2"
                      @click="openCDLS08(itemWH)"
                    >
                      <span class="badge badge-secondary">{{ itemWH.workplaceCD }}</span>
                      <span :class="{ 'fs-smaller': !itemWH.workplaceCD }">{{ itemWH.workplaceName }}</span>
                    </button>
                  </template>
                  <template v-if="condition15">
                    <!-- A3_15 -->
                    <span class="uk-text-dark-gray fs-smaller">{{ "KAFS02_34" | i18n }}</span>
                    <!-- A3_16 -->
                    <button type="button" class="btn btn-selection"
                      :disabled="checkboxWH.filter((x) => x === itemWH.frame).length > 0 && condition2"
                      @click="openKDLS10(itemWH)"
                    >
                      <span class="badge badge-secondary">{{ itemWH.workLocationCD }}</span>
                      <span :class="{ 'fs-smaller': !itemWH.workLocationCD }">{{ itemWH.workLocationName }}</span>
                    </button>
                  </template>
                </div>
                <!-- A3_4 -->
                <nts-checkbox
                  class="checkbox-text uk-text-dark-gray"
                  style="font-size: 90%"
                  v-if="itemWH.dispCheckbox && condition2 && (itemWH.actualHours.startTime != null || itemWH.actualHours.endTime != null)"
                  v-model="checkboxWH"
                  v-bind:value="itemWH.frame"
                  v-bind:disabled="itemWH.disableCheckbox"
                  >{{ "KAFS02_5" | i18n }}</nts-checkbox
                >
              </div>
            </template>
          </div>
          <template v-if="condition5 && workHourLstNumber < workHourLst.length && multipleWork">
            <div class="text-center position-relative" style="height: 55px">
              <!-- A3_18 -->
              <div class="position-absolute w-100 mt-4 pt-2">
                <span>{{ "KAFS02_10" | i18n }}</span>
              </div>
              <!-- A3_19 -->
              <div class="position-absolute w-100">
                <hr>
              </div>
              <!-- A3_17 -->
              <div class="position-absolute w-100 mt-1">
                <span @click="displayMoreItem('workHourLst')" class="fas fa-2x fa-plus-circle" style="color: #33b5e5"></span>
              </div>
            </div>
          </template>
        </div>
        <!-- Temprory WorkHour -->
        <div v-if="condition4 || (!mode && tempWorkHourLst.length > 0)">
          <div v-for="(itemTH, index) in tempWorkHourLst" :key="itemTH.frame">
            <template v-if="index < tempWorkHourLstNumber">
              <div class="row mt-3">
                <div class="col-6">{{ itemTH.title | i18n(itemTH.frame) }}</div>
                <!-- A3_2 -->
                <div class="col-6 text-right" style="font-size: 90%" v-if="condition2"><kafs00subp3 v-bind:params="itemTH.actualHours" /></div>
              </div>
              <!-- A3_3 -->
              <div style="color: red; font-size: 90%" v-if="condition2 && itemTH.errorMsg !== null">{{ itemTH.errorMsg }}</div>
              <!-- A3_1 -->
              <div class="card-body">
                <nts-time-range-input class="mb-1" v-model="itemTH.workHours" v-bind:showTile="false" v-bind:disabled="checkboxTH.filter((x) => x === itemTH.frame).length > 0 && condition2" />
                <div class="mt-1 mb-1">
                  <template v-if="false"> <!-- condition14 --> 
                    <!-- A3_24 -->
                    <span class="uk-text-dark-gray fs-smaller">{{ "KAFS02_33" | i18n }}</span>
                    <!-- A3_25 -->
                    <button type="button" class="btn btn-selection"
                      :disabled="checkboxTH.filter((x) => x === itemTH.frame).length > 0 && condition2"
                      @click="openCDLS08(itemTH)"
                    >
                      <span class="badge badge-secondary">{{ itemTH.workplaceCD }}</span>
                      <span :class="{ 'fs-smaller': !itemTH.workplaceCD }">{{ itemTH.workplaceName }}</span>
                    </button>
                  </template>
                  <template v-if="condition15">
                    <!-- A3_26 -->
                    <span class="uk-text-dark-gray fs-smaller">{{ "KAFS02_34" | i18n }}</span>
                    <!-- A3_27 -->
                    <button type="button" class="btn btn-selection"
                      :disabled="checkboxTH.filter((x) => x === itemTH.frame).length > 0 && condition2"
                      @click="openKDLS10(itemTH)"
                    >
                      <span class="badge badge-secondary">{{ itemTH.workLocationCD }}</span>
                      <span :class="{ 'fs-smaller': !itemTH.workLocationCD }">{{ itemTH.workLocationName }}</span>
                    </button>
                  </template>
                </div>
                <!-- A3_4 -->
                <nts-checkbox
                  class="checkbox-text uk-text-dark-gray"
                  style="font-size: 90%"
                  v-if="itemTH.dispCheckbox && condition2 && (itemTH.actualHours.startTime != null || itemTH.actualHours.endTime != null)"
                  v-model="checkboxTH"
                  v-bind:value="itemTH.frame"
                  v-bind:disabled="itemTH.disableCheckbox"
                  >{{ "KAFS02_5" | i18n }}</nts-checkbox
                >
              </div>
            </template>
          </div>
          <template v-if="condition4 && tempWorkHourLstNumber < tempWorkHourLst.length">
            <div class="text-center position-relative" style="height: 55px">
              <!-- A3_29 -->
              <div class="position-absolute w-100 mt-4 pt-2">
                <span>{{ "KAFS02_10" | i18n }}</span>
              </div>
              <!-- A3_30 -->
              <div class="position-absolute w-100">
                <hr>
              </div>
              <!-- A3_28 -->
              <div class="position-absolute w-100 mt-1">
                <span @click="displayMoreItem('tempWorkHourLst')" class="fas fa-2x fa-plus-circle" style="color: #33b5e5"></span>
              </div>
            </div>
          </template>
        </div>
      </div>
    </div>

    <!-- A4 -->
    <div v-if="condition6 || (!mode && goOutLst.length > 0)">
      <div class="card card-label">
        <div class="card-header uk-bg-accordion mt-2 mb-n2">
          <span>{{ "KAFS02_8" | i18n }}</span>
        </div>

        <!-- Goout WorkHour -->
        <div v-for="itemGH in goOutLst" :key="itemGH.frame">
          <div class="row mt-3">
            <div class="col-6">{{ itemGH.title | i18n(itemGH.frame) }}</div>
            <!-- A4_2 -->
            <div class="col-6 text-right" style="font-size: 90%" v-if="condition2"><kafs00subp3 v-bind:params="itemGH.actualHours" /></div>
          </div>
          <!-- A4_3 -->
          <div style="color: red; font-size: 90%" v-if="condition2 && itemGH.errorMsg !== null">{{ itemGH.errorMsg }}</div>
          <!-- A4_1 -->
          <div class="card-body">
            <nts-time-range-input v-model="itemGH.hours" v-bind:showTile="false" v-bind:disabled="checkboxGH.filter((x) => x === itemGH.frame).length > 0 && condition2" />
            <!-- A4_4 -->
            <div class="card-body w-100 mt-n3">
              <nts-switchbox v-for="option in dataSource" :key="option.id"
                  v-model="itemGH.swtModel" 
                  v-bind:value="option.id">
                      {{ option.name }}
              </nts-switchbox>
            </div>
            <!-- A4_5 -->
            <nts-checkbox
              class="checkbox-text uk-text-dark-gray mt-n3"
              style="font-size: 90%"
              v-if="itemGH.dispCheckbox && condition2 && (itemGH.actualHours.startTime != null || itemGH.actualHours.endTime != null)"
              v-model="checkboxGH"
              v-bind:value="itemGH.frame"
              v-bind:disabled="itemGH.disableCheckbox"
              >{{ "KAFS02_5" | i18n }}</nts-checkbox
            >
          </div>
        </div>
        <!-- Add more frame zone -->
        <div v-if="dispAddFrameOut">
          <div class="text-center position-relative" style="height: 35px">
            <!-- A4_7 -->
            <div class="position-absolute w-100">
              <hr>
            </div>
            <!-- A4_6 -->
            <div class="position-absolute w-100 mt-1">
              <span v-on:click="addGooutHour" class="fas fa-2x fa-plus-circle" style="color: #33b5e5"></span>
            </div>
          </div>
          <div class="text-center">{{ "KAFS02_10" | i18n }}</div>
        </div>
      </div>
    </div>
    <div>

    </div>
    <!-- A5 -->
    <div v-if="condition7 || (!mode && breakLst.length > 0)">
      <div class="card card-label">
        <div class="card-header uk-bg-accordion mt-2 mb-n2">
          <span>{{ "KAFS02_11" | i18n }}</span>
        </div>

        <!-- Break Hour -->
        <div v-for="itemBH in breakLst" :key="itemBH.frame">
          <div class="row mt-3">
            <div class="col-6">{{ itemBH.title | i18n(itemBH.frame) }}</div>
            <!-- A5_2 -->
            <div class="col-6 text-right" style="font-size: 90%" v-if="condition2"><kafs00subp3 v-bind:params="itemBH.actualHours" /></div>
          </div>
          <!-- A5_3 -->
          <div style="color: red; font-size: 90%" v-if="condition2 && itemBH.errorMsg !== null">{{ itemBH.errorMsg }}</div>
          <!-- A5_1 -->
          <div class="card-body">
            <nts-time-range-input class="mb-1" v-model="itemBH.workHours" v-bind:showTile="false" v-bind:disabled="checkboxBH.filter((x) => x === itemBH.frame).length > 0 && condition2" />
            <!-- A5_5 -->
            <nts-checkbox
              class="checkbox-text uk-text-dark-gray"
              style="font-size: 90%"
              v-if="itemBH.dispCheckbox && condition2 && (itemBH.actualHours.startTime != null || itemBH.actualHours.endTime != null)"
              v-model="checkboxBH"
              v-bind:value="itemBH.frame"
              v-bind:disabled="itemBH.disableCheckbox"
              >{{ "KAFS02_5" | i18n }}</nts-checkbox
            >
          </div>
        </div>
        <!-- Add more frame zone -->
        <div v-if="dispAddFrameBreak">
          <div class="text-center position-relative" style="height: 35px">
            <!-- A5_7 -->
            <div class="position-absolute w-100">
              <hr>
            </div>
            <!-- A5_6 -->
            <div class="position-absolute w-100 mt-1">
              <span v-on:click="addBreakHour" class="fas fa-2x fa-plus-circle" style="color: #33b5e5"></span>
            </div>
          </div>
          <div class="text-center">{{ "KAFS02_10" | i18n }}</div>
        </div>
      </div>
    </div>

    <!-- A6 -->
    <div v-if="condition8 || (!mode && childCareLst.length > 0)">
      <div class="card card-label">
        <div class="card-header uk-bg-accordion mt-2 mb-n2">
          <span>{{ "KAFS02_13" | i18n }}</span>
        </div>

        <div v-for="itemCH in childCareLst" :key="itemCH.frame">
          <div class="row mt-3">
            <div class="col-6">{{ itemCH.title | i18n(itemCH.frame) }}</div>
            <!-- A6_2 -->
            <div class="col-6 text-right" style="font-size: 90%" v-if="condition2"><kafs00subp3 v-bind:params="itemCH.actualHours" /></div>
          </div>
          <!-- A6_3 -->
          <div style="color: red; font-size: 90%" v-if="condition2 && itemCH.errorMsg !== null">{{ itemCH.errorMsg }}</div>
          <!-- A6_1 -->
          <div class="card-body">
            <nts-time-range-input class="mb-1" v-model="itemCH.workHours" v-bind:showTile="false" v-bind:disabled="checkboxCH.filter((x) => x === itemCH.frame).length > 0 && condition2" />
            <!-- A6_5 -->
            <nts-checkbox
              class="checkbox-text uk-text-dark-gray"
              style="font-size: 90%"
              v-if="itemCH.dispCheckbox && condition2 && (itemCH.actualHours.startTime != null || itemCH.actualHours.endTime != null)"
              v-model="checkboxCH"
              v-bind:value="itemCH.frame"
              v-bind:disabled="itemCH.disableCheckbox"
              >{{ "KAFS02_5" | i18n }}</nts-checkbox
            >
          </div>
        </div>
      </div>
    </div>

    <!-- A7 -->
    <div v-if="condition9 || (!mode && longTermLst.length > 0)">
      <div class="card card-label">
        <div class="card-header uk-bg-accordion mt-2 mb-n2">
          <span>{{ "KAFS02_15" | i18n }}</span>
        </div>

        <div v-for="itemLH in longTermLst" :key="itemLH.frame">
          <div class="row mt-3">
            <div class="col-6">{{ itemLH.title | i18n(itemLH.frame) }}</div>
            <!-- A7_2 -->
            <div class="col-6 text-right" style="font-size: 90%" v-if="condition2"><kafs00subp3 v-bind:params="itemLH.actualHours" /></div>
          </div>
          <!-- A7_3 -->
          <div style="color: red; font-size: 90%" v-if="condition2 && itemLH.errorMsg !== null">{{ itemLH.errorMsg }}</div>
          <!-- A7_1 -->
          <div class="card-body">
            <nts-time-range-input class="mb-1" v-model="itemLH.workHours" v-bind:showTile="false" v-bind:disabled="checkboxLH.filter((x) => x === itemLH.frame).length > 0 && condition2" />
            <!-- A7_5 -->
            <nts-checkbox
              class="checkbox-text uk-text-dark-gray"
              style="font-size: 90%"
              v-if="itemLH.dispCheckbox && condition2 && (itemLH.actualHours.startTime != null || itemLH.actualHours.endTime != null)"
              v-model="checkboxLH"
              v-bind:value="itemLH.frame"
              v-bind:disabled="itemLH.disableCheckbox"
              >{{ "KAFS02_5" | i18n }}</nts-checkbox
            >
          </div>
        </div>
      </div>
    </div>

    <!-- A12 -->
    <div v-if="condition13 || (!mode && supportLst.length > 0)">
      <div class="card card-label">
        <div class="card-header uk-bg-accordion mt-2 mb-n2">
          <span>{{ "KAFS02_31" | i18n }}</span>
        </div>
        <div v-if="(mode && condition13) || (!mode && supportLst.length > 0)">
          <div v-for="(itemSP, index) in supportLst" :key="itemSP.frame">
            <template v-if="index < supportLstNumber">
              <div class="row mt-3">
                <div class="col-6">{{ itemSP.title | i18n(itemSP.frame) }}</div>
                <!-- A12_2 -->
                <div class="col-6 text-right" style="font-size: 90%" v-if="condition2"><kafs00subp3 v-bind:params="itemSP.actualHours" /></div>
              </div>
              <!-- A12_3 -->
              <div style="color: red; font-size: 90%" v-if="condition2 && condition13 && itemSP.errorMsg !== null">{{ itemSP.errorMsg }}</div>
              <!-- A12_1 -->
              <div class="card-body">
                <nts-time-range-input class="mb-1" v-model="itemSP.workHours" v-bind:showTile="false" v-bind:disabled="checkboxSP.filter((x) => x === itemSP.frame).length > 0 && condition2" />
                <div class="mt-1 mb-1">
                  <template v-if="false"> <!-- condition14 --> 
                    <!-- A12_5 -->
                    <span class="uk-text-dark-gray fs-smaller">{{ "KAFS02_33" | i18n }}</span>
                    <!-- A12_6 -->
                    <button type="button" class="btn btn-selection"
                      :disabled="checkboxSP.filter((x) => x === itemSP.frame).length > 0 && condition2"
                      @click="openCDLS08(itemSP)"
                    >
                      <span class="badge badge-secondary">{{ itemSP.workplaceCD }}</span>
                      <span :class="{ 'fs-smaller': !itemSP.workplaceCD }">{{ itemSP.workplaceName }}</span>
                    </button>
                  </template>
                  <template v-if="condition15">
                    <!-- A12_7 -->
                    <span class="uk-text-dark-gray fs-smaller">{{ "KAFS02_34" | i18n }}</span>
                    <!-- A12_8 -->
                    <button type="button" class="btn btn-selection"
                      :disabled="checkboxSP.filter((x) => x === itemSP.frame).length > 0 && condition2"
                      @click="openKDLS10(itemSP)"
                    >
                      <span class="badge badge-secondary">{{ itemSP.workLocationCD }}</span>
                      <span :class="{ 'fs-smaller': !itemSP.workLocationCD }">{{ itemSP.workLocationName }}</span>
                    </button>
                  </template>
                </div>
                <!-- A12_4 -->
                <nts-checkbox
                  class="checkbox-text uk-text-dark-gray"
                  style="font-size: 90%"
                  v-if="itemSP.dispCheckbox && condition2 && (itemSP.actualHours.startTime != null || itemSP.actualHours.endTime != null)"
                  v-model="checkboxSP"
                  v-bind:value="itemSP.frame"
                  v-bind:disabled="itemSP.disableCheckbox"
                  >{{ "KAFS02_5" | i18n }}</nts-checkbox
                >
              </div>
            </template>
          </div>
          <template v-if="condition13 && supportLstNumber < supportLst.length">
            <div class="text-center position-relative" style="height: 55px">
              <!-- A12_10 -->
              <div class="position-absolute w-100 mt-4 pt-2">
                <span>{{ "KAFS02_10" | i18n }}</span>
              </div>
              <!-- A12_11 -->
              <div class="position-absolute w-100">
                <hr>
              </div>
              <!-- A12_9 -->
              <div class="position-absolute w-100 mt-1">
                <span @click="displayMoreItem('supportLst')" class="fas fa-2x fa-plus-circle" style="color: #33b5e5"></span>
              </div>
            </div>
          </template>
        </div>
      </div>
    </div>

    <div>
      <kafs00-c
        class="mt-3"
        v-if="kaf000_C_Params != null"
        v-bind:params="kaf000_C_Params"
        v-on:kaf000CChangeReasonCD="kaf000CChangeReasonCD"
        v-on:kaf000CChangeAppReason="kaf000CChangeAppReason"
        v-on:kafs00CValid="kafs00CValid"
      />
    </div>

    <button type="button" class="btn btn-primary btn-block text-center mb-3" v-on:click="register()" v-if="mode">{{ "KAFS02_17" | i18n }}</button>
    <button type="button" class="btn btn-primary btn-block text-center mb-3" v-on:click="register()" v-if="!mode">{{ "KAFS02_18" | i18n }}</button>
    
    <to-top />
  </div>
</template>
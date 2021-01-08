<template>
  <div class="kafs10step2">
      <!-- top-->
    <div>
      <kafs00-a
        v-if="$appContext.kaf000_A_Params != null"
        v-bind:params="$appContext.kaf000_A_Params"
      />
      <div class="accordion mb-2" v-if="$appContext.overTimeWorkHoursDto != null">
          <div class="card" v-if="true">
            <div class="card-header uk-bg-accordion">
              <button class="btn btn-link" type="button">
                  {{'KAFS00_4' | i18n}}
              </button>
            </div>
            <div class="collapse">
              <div class="card-body">
                  <kafs00subp2 v-bind:params="$appContext.overTimeWorkHoursDto" />      
              </div>
            </div>
          </div>
      </div>
    </div>

    <!-- HolidayTime -->
    <div class="card card-label">
      <!-- A2_B2_1 -->
      <div class="card-header uk-bg-accordion mt-2">
        <span>{{ "KAFS10_13" | i18n }}</span>
        <span class="badge badge-warning">必須</span>
      </div>
      <div
        v-for="(item, index) in holidayTimes"
        v-bind:key="index"
        :value="index"
      >
        <div v-show="item.visible" class="card-body">
          <div class="row mt-3">
            <div class="col-4">{{ item.title }}</div>
            <div class="col-8.5">
              <div class="row mt-0">
                  <div v-show="item.preApp.preAppDisp" class="col-6">
                      <kafs00subp1 v-bind:params="item.preApp" />
                  </div>
                  <div v-show="item.actualApp.actualDisp" class="col-6">
                      <kafs00subp1 v-bind:params="item.actualApp" />
                  </div>
              </div>
            </div>
          </div>
          <div class="card-body">
            <nts-time-editor
              v-model="item.applicationTime"
              name=""
              v-bind:enable="$appContext.c8"
            />
          </div>
        </div>
      </div>
    </div>

    <!-- OverTime -->
    <div v-show="$appContext.c9" class="card card-label">
      <!--A2_5_1-->
      <div v-show="$appContext.c9" class="card-header uk-bg-accordion mt-2">
        <span>{{ "KAFS10_17" | i18n }}</span>
        <span class="badge badge-info">任意</span>
      </div>
      <!--A2_B1_5-->
      <!-- <div
       v-if="!$valid || !$appContext.isValidateAll" 
      class="card bg-danger top-alert uk-text-danger topError mt-2"
      >
        <button class="btn btn-link uk-text-danger">
          <i class="fa fa-exclamation-circle" aria-hidden="true"></i>
          {{ "Msg_1562" | i18n }}
        </button>
      </div> -->

      <div v-for="(item, index) in overTimes" v-bind:key="index" :value="index">
        <div v-show="item.visible" class="card-body">
          <div class="row mt-3">
            <div class="col-4">{{ item.title }}</div>
            <div class="col-8.5">
              <div class="row mt-0">
                  <div v-show="item.preApp.preAppDisp" class="col-6">
                      <kafs00subp1 v-bind:params="item.preApp" />
                  </div>
                  <div v-show="item.actualApp.actualDisp" class="col-6">
                      <kafs00subp1 v-bind:params="item.actualApp" />
                  </div>
              </div>
              
            </div>
            
          </div>
          <div v-show="item.visible" class="card-body">
            <nts-time-editor
              v-model="item.applicationTime"
              name=""
              v-bind:enable="$appContext.c8"
            />
          </div>
        </div>
      </div>
    </div>

    <div>
      <kafs00-c
        v-if="$appContext.kaf000_C_Params != null"
        v-bind:params="$appContext.kaf000_C_Params"
        v-on:kaf000CChangeReasonCD="$appContext.kaf000CChangeReasonCD"
        v-on:kaf000CChangeAppReason="$appContext.kaf000CChangeAppReason"
      />
    </div>

    <!--ReasonDivergence-->
    <div class="card card-label" v-if="$appContext.c11_1">
        <!--A2_7_1-->
        <div class="card-header uk-bg-accordion" style="align-items: center">
            <v-label class="border-0 pl-0 my-n1" v-bind:constraint="validations.DivergenceReason">
                {{'KAFS10_19' | i18n}}</v-label>
            <span class="badge badge-info">任意</span>
        </div>
        <div class="card-body">
            <!--A2_7_2-->
            <div v-if="$appContext.c10">
                  <div class="mb-1">
                        <span class="small-header">{{'KAFS05_90' | i18n(reason.title)}}</span>
                  </div>
                  <div>
                        <nts-dropdown v-model="reason.selectedValue">
                            <option v-for="(item, index) in reason.dropdownList" :key="index" :value="item.code">
                                {{item.text}}
                            </option>
                        </nts-dropdown>
                  </div>
            </div>
            <!--A2_7_3-->
            <div v-if="$appContext.c11">
                  <div class="mb-1">
                        <span class="small-header">{{'KAFS05_92' | i18n(reason.title)}}</span>
                  </div>
                  <div>
                        <nts-text-area v-model="reason.reason" />
                  </div>
            </div>
        </div>
    </div>

    <!--A2_8_1-->
    <div class="card card-label">
        <!-- huytodo v-on:click="$appContext.register" -->
      <button
        type="button"
        class="btn btn-block btn-primary btn-lg"
      >
        {{($appContext.modeNew ? 'KAFS10_20' : 'KAFS10_21') | i18n}}
      </button>
      <!-- A2_C1_2 -->
      <!-- <button
        type="button"
        class="btn btn-block btn-secondary"
        v-on:click="backStep1"
      >
        {{ "KAFS05_12" | i18n }}
      </button> -->
    </div>
  </div>
</template>
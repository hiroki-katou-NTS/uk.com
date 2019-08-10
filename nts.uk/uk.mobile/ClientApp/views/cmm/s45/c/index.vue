<template>
  <div class="cmms45c">
    <div class="modal-header">
      <a v-on:click="back()">
        <i class="fas fa-arrow-circle-left"></i>
        {{ 'CMMS45_17' | i18n }}
      </a>
    </div>
    <div class="row mb-2">
      <div class="col-6 text-left uk-text-blue">
        <a v-on:click="toPreviousApp" v-bind:class="{ 'd-none': isFirstApp() && !isEmptyApp() }">
          <i class="fas fa-arrow-circle-left"></i>
          {{'CMMS45_18' | i18n}}
        </a>
      </div>
      <div class="col-6 text-right uk-text-blue">
        <a v-on:click="toNextApp" v-bind:class="{ 'd-none': isLastApp() && !isEmptyApp() }">
          {{'CMMS45_19' | i18n}}
          <i class="fas fa-arrow-circle-right"></i>
        </a>
      </div>
    </div>
    <div class="row bg-grey-200 border uk-border-gray border-right-0 border-left-0">
      <div class="col-12">
        <div class="row p-2 pl-3 pr-3 ">
          <div class="mb-2 p-2 status-label align-top col-3" v-bind:class="appState.getClass">{{ appState.getName | i18n }}</div>
          <div class="col-9">
            <span class="ml-1">{{ appState.getNote | i18n }}</span>
            <br />
            <span class="uk-text-blue" v-on:click="reverseApproval" v-bind:class="appState.appStatus == 3 || appState.appStatus == 4">
              {{'CMMS45_20' | i18n}}
              <i class="uk-text-blue"
                v-bind:class="{ 'fas fa-chevron-down': !showApproval, 'fas fa-chevron-up': showApproval}"
              ></i>
            </span>
          </div>
        </div>
        <div v-if="showApproval">
          <approved v-model="selected" class="mt-2">
            <template v-slot:buttons>
              <!-- Khi một button bất kỳ được click, giá trị được emit ra là thứ tự của button -->
              <button v-for="(phase, phaseIndex) in phaseLst" v-bind:key="phaseIndex" v-bind:class="[phase.className, !phase.isExist ? 'corner-line' : '' ]" 
                v-bind:disabled="!phase.isExist" class="h-100">{{ phase.phaseText | i18n }}</button>
            </template>
            <template v-slot:popovers>
              <!-- duyệt theo thứ tự button ở đây -->
              <div v-for="(phase, phaseIndex) in phaseLst" v-bind:key="phaseIndex">
                <template v-if="selected == phaseIndex">
                  <div>{{phase.infoLabel | i18n(phase.phaseOrder.toString())}}</div>
                  <div>
                    <table class="table table-sm table-bordered m-0 bg-white">
                      <tbody>
                        <tr v-for="(frame, frameIndex) in phase.listApprovalFrame" v-bind:key="frameIndex">
                          <td v-bind:class="{ 'uk-bg-powder-blue' : frame.approvalAtrValue==1, 'uk-bg-light-coral' : frame.approvalAtrValue==2 }"
                            class="fixed-column-width align-middle text-center">
                            <span v-if="frame.approvalAtrValue==1">
                              <i class="fa fa-check"></i>   
                            </span>
                            <span v-else-if="frame.approvalAtrValue==2">
                              <i class="fa fa-times"></i>
                            </span>
                          </td>
                          <td>
                            <span v-if="frame.approvalAtrValue==1 || frame.approvalAtrValue==2">
                              <span v-if="frame.approverName">{{ frame.approverName }}</span>
                              <span v-else-if="frame.representerName">{{ frame.representerName }}</span>
                              <br/>
                              <span>{{ frame.approvalReason }}</span>
                            </span>  
                            <span v-else>
                              <span v-for="(approver, approverIndex) in frame.listApprover" v-bind:key="approverIndex">
                                <span>
                                  {{ approver.approverName }}
                                </span>
                                <span v-if="approver.representerName">
                                  ({{ approver.representerName }})
                                </span>
                                <span v-if="approverIndex < frame.listApprover.length - 1">,</span>
                              </span>
                            </span>    
                          </td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </template>
              </div>
            </template>
          </approved>
        </div>
        <div class="row pl-3 pr-3 mb-2" v-if="reversionReason"> 
          <div class="col-12 uk-bg-light-coral">
            <div class="row ml-0 mr-0 pt-1 pb-1">{{ 'CMMS45_58' | i18n }}</div>
            <hr class="mt-0 mb-0"/>
            <div class="row ml-2 mr-2 pt-1 pb-1">{{ reversionReason || 'CMMS45_15' | i18n }}</div>
          </div>
        </div>
      </div> 
    </div>
    <div>
      <app1 v-if="true" v-bind:params="{appOvertime: appOvertime}" />
      <app2 v-if="false" />
      <app3 v-if="false" />
    </div>
    <div
      class="fixed-action-btn" v-show="displayEditFloat"
      v-float-action="{ icon: 'fas fa-pen', background: 'uk-bg-sea-green', forceground: 'uk-text-dark-gray' }"
    >
      <ul>
        <li class="uk-bg-white" v-on:click="deleteApp" v-show="displayDeleteButton">
          <span class="uk-text-dark-gray">{{'CMMS45_60' | i18n}}</span>
          <i class="fas fa-trash-alt uk-text-dark-gray"></i>
        </li>
        <li class="uk-bg-white" v-on:click="updateApp" v-show="displayUpdateButton">
          <span class="uk-text-dark-gray">{{'CMMS45_61' | i18n}}</span>
          <i class="fas fa-pen uk-text-dark-gray"></i>
        </li>
      </ul>
    </div>
  </div>
</template>
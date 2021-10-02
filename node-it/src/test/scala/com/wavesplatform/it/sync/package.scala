package com.wavesplatform.it

import com.wavesplatform.api.http.ApiError.TransactionNotAllowedByAssetScript
import com.wavesplatform.api.http.requests.IssueRequest
import com.wavesplatform.common.utils.EitherExt2
import com.wavesplatform.it.api.SyncHttpApi.AssertiveApiError
import com.wavesplatform.lang.script.Script
import com.wavesplatform.lang.v1.estimator.v2.ScriptEstimatorV2
import com.wavesplatform.protobuf.transaction.{DataTransactionData, PBTransactions}
import com.wavesplatform.state.DataEntry
import com.wavesplatform.test._
import com.wavesplatform.transaction.assets.IssueTransaction
import com.wavesplatform.transaction.smart.script.ScriptCompiler

package object sync {
  val smartFee: Long                   = 0.04.TN
  val minFee: Long                     = 0.02.TN
  val leasingFee: Long                 = 0.02.TN
  val issueFee: Long                   = 1000.TN
  val reissueFee: Long                 = 1000.TN
  val reissueReducedFee: Long          = 1.TN
  val burnFee: Long                    = 0.02.TN
  val aliasFeeAmount: Long                  = 10.TN
  val invokeFee: Long                  = 1.TN
  val sponsorFee: Long                 = 10.TN
  val sponsorReducedFee: Long          = 0.02.TN
  val setAssetScriptFee: Long          = 1.TN + smartFee
  val setScriptFee: Long               = 1.TN
  val transferAmount: Long             = 1100.TN
  val tokenTransferAmount: Long        = 10.TN
  val leasingAmount: Long              = transferAmount
  val issueAmount: Long                = transferAmount
  val massTransferFeePerTransfer: Long = 0.01.TN
  val someAssetAmount: Long            = 9999999999999L
  val matcherFee: Long                 = 0.04.TN
  val orderFee: Long                   = matcherFee
  val smartMatcherFee: Long            = 0.08.TN
  val smartMinFee: Long                = minFee + smartFee

  def calcDataFee(data: List[DataEntry[_]], txVersion: Byte): Long = {
    if (txVersion < 2) {
      val dataSize = data.map(_.toBytes.length).sum + 128
      if (dataSize > 1024) {
        minFee * (dataSize / 1024 + 1)
      } else minFee
    } else {
      val payload   = DataTransactionData(data.map(dataEntry => PBTransactions.toPBDataEntry(dataEntry))).toByteArray
      val feeInUnit = 1 + (payload.length - 1) / 1024
      feeInUnit * 2000000
    }
  }

  def calcMassTransferFee(numberOfRecipients: Int): Long = {
    minFee + massTransferFeePerTransfer * (numberOfRecipients + 1)
  }

  val supportedVersions: List[Byte]               = List(1, 2, 3)
  val burnTxSupportedVersions: List[Byte]         = List(1, 2, 3)
  val leaseTxSupportedVersions: List[Byte]        = List(1, 2, 3)
  val dataTxSupportedVersions: List[Byte]         = List(1, 2)
  val massTransferTxSupportedVersions: List[Byte] = List(1, 2)
  val sponsorshipTxSupportedVersions: List[Byte]  = List(1, 2)
  val setAssetScrTxSupportedVersions: List[Byte]  = List(1, 2)
  val issueTxSupportedVersions: List[Byte]        = List(1, 2, 3)
  val transferTxSupportedVersions: List[Byte]     = List(1, 2, 3)
  val aliasTxSupportedVersions: List[Byte]        = List(1, 2, 3)
  val reissueTxSupportedVersions: List[Byte]      = List(1, 2, 3)

  val script: Script          = ScriptCompiler(s"""true""".stripMargin, isAssetScript = false, ScriptEstimatorV2).explicitGet()._1
  val scriptBase64: String    = script.bytes().base64
  val scriptBase64Raw: String = script.bytes().base64Raw

  val errNotAllowedByToken = "Transaction is not allowed by token-script"
  val errNotAllowedByTokenApiError: AssertiveApiError =
    AssertiveApiError(
      TransactionNotAllowedByAssetScript.Id,
      TransactionNotAllowedByAssetScript.Message,
      TransactionNotAllowedByAssetScript.Code
    )

  def createIssueRequest(tx: IssueTransaction): IssueRequest = {
    import tx._
    IssueRequest(
      Some(tx.version),
      None,
      Some(tx.sender.toString),
      tx.name.toStringUtf8,
      tx.description.toStringUtf8,
      quantity,
      decimals,
      reissuable,
      tx.script.map(_.bytes().base64),
      fee,
      Some(timestamp),
      proofs.headOption,
      Some(proofs)
    )
  }

}

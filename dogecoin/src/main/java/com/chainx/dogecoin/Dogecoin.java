package com.chainx.dogecoin;

import android.text.TextUtils;
import java.util.regex.Pattern;

public class Dogecoin {
    static {
        System.loadLibrary("dogecoin_dll");
    }

    /**
     * Generate private key from mnemonic
     *
     * - [`phrase`]: root phrase
     * - [`pd_passphrase`]: pass phrase
     *
     * Return the private key hex string.
     */
    public static native String generate_my_privkey_dogecoin(String phrase, String pd_passphrase);

    /**
     * Generate pubkey from privkey
     *
     * - [`privkey`]: private key
     *
     * Returns: pubkey string
     */
    public static native String generate_my_pubkey_dogecoin(String privkey);

    /**
     * Generate dogecoin p2kh address from pubkey
     *
     * - [`pubkey`]: pubkey hex string
     * - [`network`]: network string, support [`mainnet` ,`testnet`]
     *
     * Return the dogecoin address string.
     */
    public static native String generate_address_dogecoin(String pubkey, String network);

    /**
     * Generate redeem script
     *
     * - [`pubkeys`]: Hex string concatenated with multiple pubkeys
     * - [`threshold`]: threshold number
     *
     * Return the dogecoin redeem script.
     */
    public static native String generate_redeem_script_dogecoin(String pubkeys, long threshold);

    /**
     * Generate dogecoin p2sh address
     *
     * - [`redeem_script`]: redeem script
     * - [`network`]: network string, support [`mainnet` ,`testnet`]
     *
     * Return the dogecoin address string.
     */
    public static native String generate_multisig_address_dogecoin(String redeem_script, String network);

    /**
     * Add the first input to initialize basic transactions
     *
     * - [`txid`]: utxo's txid
     * - [`index`]: utxo's index
     *
     * Return the base tx hex string with one input.
     */
    public static native String generate_base_tx_dogecoin(String txid, long index);

    /**
     * Increase the input with txid and index
     *
     * - [`base_tx`]: base transaction hex string
     * - [`txid`]: utxo's txid
     * - [`index`]: utxo's index
     *
     * Return the base tx hex string with more input.
     */
    public static native String add_input_dogecoin(String base_tx, String txid, long index);

    /**
     * Increase the output with address and amount
     *
     * - [`base_tx`]: base transaction hex string
     * - [`address`]: output address
     * - [`amount`]: output amount
     *
     * Return the base tx hex string with more output.
     */
    public static native String add_output_dogecoin(String base_tx, String address, long amount);

    /**
     * Generate sighash/message to sign.
     *
     * NOTE: Through sig_type and script input different, support p2kh and p2sh two types of sighash.
     *
     * - [`base_tx`]: base transaction hex string
     * - [`txid`]: utxo's txid
     * - [`index`]: utxo's index
     * - [`sig_type`]: support [`0`, `1``], 0 is p2kh, 1 is p2sh
     * - [`script`]: When p2kh, script input user pubkey, when p2sh script input redeem script
     *
     * Return the sighash string.
     */
    public static native String generate_sighash_dogecoin(String base_tx,
                                String txid,
                                    long index,
                                    long sig_type,
                                String script);

    /**
     * Generate ecdsa signature.
     *
     * [`message`]: Awaiting signed sighash/message
     * [`privkey`]: private key
     *
     * Return the signature hex string.
     */
    public static native String generate_signature_dogecoin(String message, String privkey);

    /**
     * Combining signatures into transaction.
     *
     * - [`base_tx`]: base transaction hex string.
     * - [`signature`]: signature of sighash
     * - [`txid`]: utxo's txid
     * - [`index`]: utxo's index
     * - [`sig_type`]: support [`0`, `1``], 0 is p2kh, 1 is p2sh
     * - [`script`]: When p2kh, script input user pubkey, when p2sh script input redeem script
     *
     * Return transaction with one more signature.
     */
    public static native String build_tx_dogecoin(String base_tx,
                        String signature,
                        String txid,
                            long index,
                            long sig_type,
                        String script);


    public static boolean isHexStr(String str)
    {
        String pattern = "[A-Fa-f0-9]+$";
        return Pattern.compile(pattern).matcher(str).matches();
    }

    /**
     * Generate private key from mnemonic
     *
     * - parameter phrase: root phrase
     * - parameter pd_passphrase: pass phrase
     * - returns: the private key hex string
     */
    public static String generateMyPrivkey(String phrase, String pd_passphrase) throws DogecoinExcpetion {
        String result = generate_my_privkey_dogecoin(phrase, pd_passphrase);
        if (isHexStr(result)) {
            return result;
        } else {
            throw new DogecoinExcpetion("generateMyPrivkey", "generate_my_privkey_dogecoin", result);
        }
    }

    /**
     * Generate pubkey from privkey
     *
     * - parameter privkey: private key
     * - returns: pubkey string
     */
    public static String generateMyPubkey(String privkey) throws DogecoinExcpetion {
        String result = generate_my_pubkey_dogecoin(privkey);
        if (isHexStr(result)) {
            return result;
        }else{
            throw new DogecoinExcpetion("generateMyPubkey", "generate_my_pubkey_dogecoin", result);
        }
    }

    /**
     * Generate dogecoin p2kh address from pubkey
     *
     * - parameter pubkey: pubkey hex string
     * - parameter network: network string, support  ["mainnet", "testnet"]
     *
     * - returns: the dogecoin address string.
     */
    public static String generateAddress(String pubkey, String network){
        return generate_address_dogecoin(pubkey, network);
    }

    /**
     * Generate redeem script
     *
     * - parameter pubkeys: Hex string concatenated with multiple pubkeys
     * - parameter threshold: threshold number
     * - returns: the dogecoin redeem script.
     */
    public static String generateRedeemScript(String[] pubkeys, long threshold) throws DogecoinExcpetion {
        String result = generate_redeem_script_dogecoin(TextUtils.join("", pubkeys).toString(), threshold);
        if (isHexStr(result)) {
            return result;
        }else{
            throw new DogecoinExcpetion("generateRedeemScript", "generate_redeem_script_dogecoin", result);
        }
    }

    /**
     * Generate dogecoin p2sh address
     *
     * - parameter redeem_script: redeem script
     * - parameter network: network string, support ["mainnet", "testnet"]
     * - returns: the dogecoin address string.
     */
    public static String generateMultisigAddress(String redeem_script, String network) throws DogecoinExcpetion {
        return generate_multisig_address_dogecoin(redeem_script, network);
    }

    /**
     * Add the first input to initialize basic transactions
     *
     * - parameter txids: utxo's txid array
     * - parameter indexs: utxo's index array
     * - parameter addresses: utxo's addresse array
     * - parameter amounts: utxo's amount array
     * - returns: the dogecoin raw tx hex string without signature
     */
    public static String generateRawTx(String[] txids, long[] indexs, String[] addresses, long[] amounts) throws DogecoinExcpetion {
        if ((txids.length != indexs.length) || (addresses.length != amounts.length)) {
            throw new DogecoinExcpetion("generateRawTx", "", "input or output must be equal");
        }
        String base_tx = generate_base_tx_dogecoin(txids[0], indexs[0]);

        for (int i = 1; i < txids.length; i++) {
            base_tx = add_input_dogecoin(base_tx,  txids[i], indexs[i]);
        }
        for (int i = 0; i < addresses.length; i++) {
            base_tx = add_output_dogecoin(base_tx, addresses[i], amounts[i]);
        }

        if (isHexStr(base_tx)) {
            return base_tx;
        }else{
            throw new DogecoinExcpetion("generateRawTx", "", base_tx);
        }
    }

    /**
     * Generate sighash/message to sign. Through sig_type and script input different, support p2kh and p2sh two types of sighash
     *
     * - parameter base_tx: base transaction hex string
     * - parameter txid: utxo's txid
     * - parameter index: utxo's index
     * - parameter sig_type: support  [0, 1]. 0 is p2kh, 1 is p2sh
     * - parameter script: When p2kh, script input user pubkey, when p2sh script input redeem script
     * - returns: the sighash hex string
     */
    public static String generateSighash(String base_tx, String txid, long index, long sig_type, String script) throws DogecoinExcpetion {
        String result = generate_sighash_dogecoin(base_tx, txid, index, sig_type, script);
        if (isHexStr(result)) {
            return result;
        }else{
            throw new DogecoinExcpetion("generateSighash", "generate_sighash_dogecoin", result);
        }
    }

    /**
     * Generate ecdsa signature.
     *
     * - parameter message: Awaiting signed sighash/message
     * - parameter privkey: private key
     * - returns: the signature hex string.
     */
    public static String generateSignature(String message, String privkey) throws DogecoinExcpetion {
        String result = generate_signature_dogecoin(message, privkey);
        if (isHexStr(result)) {
            return result;
        }else{
            throw new DogecoinExcpetion("generateSignature", "generate_signature_dogecoin", result);
        }
    }

    /**
     * Combining signatures into transaction.
     *
     * - parameter base_tx: base transaction hex string.
     * - parameter signature: signature of sighash
     * - parameter txid: utxo's txid
     * - parameter index: utxo's index
     * - parameter sig_type: support  [0, 1]. 0 is p2kh, 1 is p2sh
     * - parameter script: When p2kh, script input user pubkey, when p2sh script input redeem script
     * - returns: base transaction with one more signature.
     */
    public static String buildTx(String base_tx, String signature, String txid, long index, long sig_type, String script) throws DogecoinExcpetion {
        String result = build_tx_dogecoin(base_tx, signature, txid, index, sig_type, script);
        if (isHexStr(result)) {
            return result;
        }else{
            throw new DogecoinExcpetion("buildTx", "build_tx_dogecoin", result);
        }
    }
}

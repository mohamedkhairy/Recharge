import com.example.home.domain.useCase.EnterPhoneNumberUseCase
import com.example.home.fakeData.ZainInvalidFakeData.emptyPhoneNum
import com.example.home.fakeData.ZainInvalidFakeData.invalidPhoneNum
import com.example.home.fakeData.ZainValidFakeData.validPhoneNum
import com.example.recharge.R
import com.example.sharedData.model.RechargeModel
import com.example.utils.core.Constant.zainRecognizekey
import com.example.utils.resourceProvider.ResourceProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import kotlin.test.assertFailsWith

@ExperimentalCoroutinesApi
class EnterPhoneNumberUseCaseTest {

    @Mock
    private lateinit var resourceProvider: ResourceProvider

    private lateinit var enterPhoneNumberUseCase: EnterPhoneNumberUseCase

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined) // Set the main dispatcher for tests
        enterPhoneNumberUseCase = EnterPhoneNumberUseCase(Dispatchers.Unconfined, resourceProvider)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher after the test
    }

    @Test
    fun `test execute with empty phone number throws error`() = runTest(testDispatcher) {
        val errorMessage = "Phone number length is incorrect"

        `when`(resourceProvider.getText(R.string.phone_num_length_message)).thenReturn(errorMessage)

        val exception = assertFailsWith<IllegalStateException> {
            enterPhoneNumberUseCase.execute(emptyPhoneNum).collect()
        }


        assertEquals(errorMessage, exception.message)
    }

    @Test
    fun `test execute with invalid Zain number throws error`() = runTest(testDispatcher) {
        val errorMessage = "Invalid Zain number"

        `when`(resourceProvider.getText(R.string.zain_validation_message)).thenReturn(errorMessage)

        val exception = assertFailsWith<IllegalStateException> {
            enterPhoneNumberUseCase.execute(invalidPhoneNum).collect()
        }

        assertEquals("$errorMessage $zainRecognizekey", exception.message)
    }

    @Test
    fun `test execute with valid Zain number emits RechargeModel`() = runTest(testDispatcher) {
        val expectedModel = RechargeModel(phoneNum = validPhoneNum, rechargeType = null)


        val result = enterPhoneNumberUseCase.execute(validPhoneNum).toList()

        assertEquals(1, result.size)
        assertEquals(expectedModel.phoneNum, result.first().phoneNum)
    }
}
